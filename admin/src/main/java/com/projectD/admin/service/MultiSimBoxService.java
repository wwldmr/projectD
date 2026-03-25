package com.projectD.admin.service;

import com.projectD.admin.dto.SimDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class MultiSimBoxService {
    private final WebClient multiSimBoxClient;

    @Value("${backend.endpoints.sim-cards}")
    private String simCardsEndpoint;

    public Flux<SimDto> getAllSimCards() {
        return multiSimBoxClient
                .get()
                .uri(simCardsEndpoint)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                            .flatMap(error -> Mono.error(new RuntimeException("backend error: " + error)))
                )
                .bodyToFlux(SimDto.class)
                .timeout(Duration.ofSeconds(10))
                .doOnError(error -> log.error("Error fetching SIM cards", error));
    }

    public Mono<SimDto> getSimCardById(String id) {
        return multiSimBoxClient
                .get()
                .uri(simCardsEndpoint + "/{id}", id)
                .retrieve()
                .onStatus(status -> status.value() == 404, response ->
                        response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "SIM card not found: " + id + ". " + error)))
                )
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new ResponseStatusException(HttpStatus.BAD_GATEWAY, "backend error: " + error)))
                )
                .bodyToMono(SimDto.class);
    }

    public Mono<SimDto> createSimCard(SimDto simCardDto) {
        return multiSimBoxClient
                .post()
                .uri(simCardsEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(simCardDto))
                .retrieve()
                .bodyToMono(SimDto.class);
    }
}
