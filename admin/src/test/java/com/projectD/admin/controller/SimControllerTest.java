package com.projectD.admin.controller;

import com.projectD.admin.dto.SimDto;
import com.projectD.admin.service.MultiSimBoxService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimControllerTest {

    @Mock
    private MultiSimBoxService multiSimBoxService;

    @InjectMocks
    private SimController simController;

    @Test
    void getAllSimCardsReturnsServiceData() {
        SimDto first = new SimDto(
                1L, "MTS", "+79031234567", "8999000000000000001", "ACTIVE", "В эксплуатации",
                null, null, null, null, null, null
        );
        SimDto second = new SimDto(
                2L, "Beeline", "+79035554433", "8999000000000000002", "SUSPENDED", "Ограничена",
                null, null, null, null, null, null
        );
        when(multiSimBoxService.getAllSimCards()).thenReturn(Flux.just(first, second));

        StepVerifier.create(simController.getAllSimCards())
                .expectNext(first)
                .expectNext(second)
                .verifyComplete();

        verify(multiSimBoxService).getAllSimCards();
    }

    @Test
    void getSimCardByIdReturnsServiceData() {
        String id = "10";
        SimDto expected = new SimDto(
                10L, "Megafon", "+79261112233", "8999000000000000010", "ACTIVE", "В эксплуатации",
                "2025-10-01 10:00", "2026-10-01 10:00", 10045L, "Работает", "Москва", "Cisco"
        );
        when(multiSimBoxService.getSimCardById(id)).thenReturn(Mono.just(expected));

        StepVerifier.create(simController.getSimCardById(id))
                .expectNext(expected)
                .verifyComplete();

        verify(multiSimBoxService).getSimCardById(id);
    }

    @Test
    void createSimCardReturnsCreatedEntity() {
        SimDto request = new SimDto(
                0L, "Tele2", "+79037778899", "8999000000000000011", "NEW", "Новая",
                null, null, null, null, null, null
        );
        SimDto created = new SimDto(
                11L, "Tele2", "+79037778899", "8999000000000000011", "NEW", "Новая",
                null, null, null, null, null, null
        );
        when(multiSimBoxService.createSimCard(request)).thenReturn(Mono.just(created));

        StepVerifier.create(simController.createSimCard(request))
                .expectNext(created)
                .verifyComplete();

        verify(multiSimBoxService).createSimCard(request);
    }
}
