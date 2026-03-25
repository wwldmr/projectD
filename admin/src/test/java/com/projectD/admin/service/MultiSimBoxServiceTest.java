package com.projectD.admin.service;

import com.projectD.admin.dto.SimDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MultiSimBoxServiceTest {

    @Test
    void getAllSimCardsReturnsDataFromBackend() {
        String body = "[{\"id\":1,\"mobileOperator\":\"MTS\",\"defNumber\":\"+79031234567\",\"iccid\":\"8999000000000000001\",\"simStatus\":\"ACTIVE\",\"simStatusBdo\":\"В эксплуатации\"}]";
        MultiSimBoxService service = buildService(request -> Mono.just(jsonResponse(HttpStatus.OK, body)));

        StepVerifier.create(service.getAllSimCards())
                .assertNext(sim -> {
                    assertEquals(1L, sim.getId());
                    assertEquals("8999000000000000001", sim.getIccid());
                    assertEquals("MTS", sim.getMobileOperator());
                })
                .verifyComplete();
    }

    @Test
    void getAllSimCardsMapsBackendErrors() {
        MultiSimBoxService service = buildService(request -> Mono.just(jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, "boom")));

        StepVerifier.create(service.getAllSimCards())
                .expectErrorMatches(error -> error instanceof RuntimeException
                        && error.getMessage().contains("backend error: boom"))
                .verify();
    }

    @Test
    void getAllSimCardsTimesOutWhenBackendDoesNotRespond() {
        MultiSimBoxService service = buildService(request -> Mono.never());

        StepVerifier.withVirtualTime(service::getAllSimCards)
                .thenAwait(Duration.ofSeconds(11))
                .expectError(TimeoutException.class)
                .verify();
    }

    @Test
    void getSimCardByIdUsesPathVariable() {
        AtomicReference<ClientRequest> captured = new AtomicReference<>();
        String body = "{\"id\":10,\"mobileOperator\":\"Megafon\",\"defNumber\":\"+79261112233\",\"iccid\":\"8999000000000000010\",\"simStatus\":\"ACTIVE\",\"simStatusBdo\":\"В эксплуатации\",\"activationDate\":\"2025-10-01 10:00\",\"deactivationDate\":\"2026-10-01 10:00\",\"objectNumber\":10045,\"objectStatus\":\"Работает\",\"objectAddress\":\"Москва\",\"equipmentModel\":\"Cisco\"}";
        MultiSimBoxService service = buildService(request -> {
            captured.set(request);
            return Mono.just(jsonResponse(HttpStatus.OK, body));
        });

        StepVerifier.create(service.getSimCardById("10"))
                .assertNext(sim -> assertEquals(10L, sim.getId()))
                .verifyComplete();

        assertEquals(HttpMethod.GET, captured.get().method());
        assertTrue(captured.get().url().getPath().endsWith("/api/sim-cards/10"));
    }

    @Test
    void createSimCardSendsPostAndReturnsCreatedEntity() {
        AtomicReference<ClientRequest> captured = new AtomicReference<>();
        String body = "{\"id\":11,\"mobileOperator\":\"Tele2\",\"defNumber\":\"+79037778899\",\"iccid\":\"8999000000000000011\",\"simStatus\":\"NEW\",\"simStatusBdo\":\"Новая\"}";
        MultiSimBoxService service = buildService(request -> {
            captured.set(request);
            return Mono.just(jsonResponse(HttpStatus.OK, body));
        });

        SimDto request = new SimDto(
                0L, "Tele2", "+79037778899", "8999000000000000011", "NEW", "Новая",
                null, null, null, null, null, null
        );

        StepVerifier.create(service.createSimCard(request))
                .assertNext(created -> assertEquals(11L, created.getId()))
                .verifyComplete();

        assertEquals(HttpMethod.POST, captured.get().method());
        assertTrue(captured.get().url().getPath().endsWith("/api/sim-cards"));
    }

    private MultiSimBoxService buildService(ExchangeFunction exchangeFunction) {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .exchangeFunction(exchangeFunction)
                .build();

        MultiSimBoxService service = new MultiSimBoxService(webClient);
        ReflectionTestUtils.setField(service, "simCardsEndpoint", "/api/sim-cards");
        return service;
    }

    private ClientResponse jsonResponse(HttpStatus status, String body) {
        return ClientResponse.create(status)
                .header("Content-Type", "application/json")
                .body(body)
                .build();
    }
}
