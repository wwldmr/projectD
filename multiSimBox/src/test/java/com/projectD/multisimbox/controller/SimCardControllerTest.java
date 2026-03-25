package com.projectD.multisimbox.controller;

import com.projectD.multisimbox.dto.SimCardRequest;
import com.projectD.multisimbox.dto.SimCardResponse;
import com.projectD.multisimbox.service.SimCardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimCardControllerTest {

    @Mock
    private SimCardService simCardService;

    @InjectMocks
    private SimCardController simCardController;

    @Test
    void getAllSimCardsReturnsOkWithServiceData() {
        SimCardResponse first = new SimCardResponse(
                1L, "MTS", "+79031234567", "8999000000000000001", "ACTIVE", "В эксплуатации",
                null, null, null, null, null, null
        );
        SimCardResponse second = new SimCardResponse(
                2L, "Beeline", "+79035554433", "8999000000000000002", "SUSPENDED", "Ограничена",
                null, null, null, null, null, null
        );
        List<SimCardResponse> expected = List.of(first, second);
        when(simCardService.getAllSimCards()).thenReturn(expected);

        ResponseEntity<List<SimCardResponse>> response = simCardController.getAllSimCards();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(simCardService).getAllSimCards();
    }

    @Test
    void getSimCardByIdReturnsOkWithServiceData() {
        long id = 10L;
        SimCardResponse expected = new SimCardResponse(
                id, "Megafon", "+79261112233", "8999000000000000010", "ACTIVE", "В эксплуатации",
                "2025-10-01 10:00", "2026-10-01 10:00", 10045L, "Работает", "Москва", "Cisco"
        );
        when(simCardService.getSimCardById(id)).thenReturn(expected);

        ResponseEntity<SimCardResponse> response = simCardController.getSimCardById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(simCardService).getSimCardById(id);
    }

    @Test
    void createSimCardReturnsCreatedEntity() {
        SimCardRequest request = new SimCardRequest(0L, "8999000000000000011", "Tele2", "444444444444444");
        SimCardResponse created = new SimCardResponse(
                11L, "Tele2", "+79037778899", "8999000000000000011", "NEW", "Новая",
                null, null, null, null, null, null
        );
        when(simCardService.createSimCard(request)).thenReturn(created);

        ResponseEntity<SimCardResponse> response = simCardController.createSimCard(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(created, response.getBody());
        verify(simCardService).createSimCard(request);
    }
}
