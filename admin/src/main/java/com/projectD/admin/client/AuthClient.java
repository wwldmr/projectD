package com.projectD.admin.client;

import com.projectD.admin.dto.LoginRequest;
import com.projectD.admin.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthClient {
    private final WebClient backendWebClient;

    public Mono<UserDto> validate(LoginRequest request) {
        return backendWebClient.post()
                .uri("/api/auth/validate")
                .bodyValue(request)
                .retrieve()
                .onStatus(status -> status.value() == 401 || status.value() == 403, response ->
                        response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad credentials. " + error)))
                )
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Authentication backend error. " + error)))
                )
                .bodyToMono(UserDto.class);
    }
}
