package com.projectD.admin.controller;

import com.projectD.admin.service.MultiSimBoxService;
import com.projectD.admin.dto.SimDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/sim-cards")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class SimController {
    private final MultiSimBoxService multiSimBoxService;

    @Operation(summary = "Получить все симкарты")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<SimDto> getAllSimCards() {
        return multiSimBoxService.getAllSimCards();
    }

    @Operation(summary = "Получить sim карту по id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<SimDto> getSimCardById(@PathVariable String id) {
        return multiSimBoxService.getSimCardById(id);
    }

    @Operation(summary = "Создать новую sim карту")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<SimDto> createSimCard(@Valid @RequestBody SimDto simDto) {
        return multiSimBoxService.createSimCard(simDto);
    }
}
