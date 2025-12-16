package com.projectD.admin.controller;

import com.projectD.admin.service.SimService;
import com.projectD.model.dto.SimDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/sim_card")
@CrossOrigin(origins = "http://localhost:8000", allowCredentials = "true")
@AllArgsConstructor
public class SimController {
    private final SimService simService;

    @GetMapping
    public ResponseEntity<Page<SimDto>> getAllSims(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String operator,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String equipment,
            Pageable pageable) {
        Page<SimDto> page = simService.findAll(status, operator, address, equipment, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimDto> getSimByID(@PathVariable("id") Long id) {
        SimDto simDto = simService.findById((id));
        return ResponseEntity.ok(simDto);
    }

    @PostMapping("/create")
    public ResponseEntity<SimDto> createSim(@RequestBody SimDto simDto) {
        SimDto created = simService.createSim(simDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SimDto> updateSim(
            @PathVariable Long id,
            @RequestBody SimDto simDto
    ) {
        SimDto updated = simService.updateSim(id, simDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSim(@PathVariable Long id) {
        simService.deleteSim(id);
        return ResponseEntity.noContent().build();
    }
}
