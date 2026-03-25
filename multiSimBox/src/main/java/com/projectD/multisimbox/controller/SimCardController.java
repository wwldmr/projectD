package com.projectD.multisimbox.controller;

import com.projectD.multisimbox.dto.SimCardRequest;
import com.projectD.multisimbox.dto.SimCardResponse;
import com.projectD.multisimbox.service.SimCardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sim-cards")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class SimCardController {
    private final SimCardService simCardService;

    @GetMapping
    public ResponseEntity<List<SimCardResponse>> getAllSimCards() {
        List<SimCardResponse> simCards = simCardService.getAllSimCards();
        return ResponseEntity.ok(simCards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimCardResponse> getSimCardById(@PathVariable("id") long id) {
        SimCardResponse simCard = simCardService.getSimCardById(id);
        return ResponseEntity.ok(simCard);
    }

    @PostMapping
    public ResponseEntity<SimCardResponse> createSimCard(@Valid @RequestBody SimCardRequest request) {
        SimCardResponse created = simCardService.createSimCard(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
