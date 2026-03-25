package com.projectD.multisimbox.controller;

import com.projectD.multisimbox.dto.SimCardResponse;
import com.projectD.multisimbox.service.SimCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/sim-cards")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class PublicSimCardController {
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
}
