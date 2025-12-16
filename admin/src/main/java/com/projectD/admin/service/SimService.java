package com.projectD.admin.service;

import com.projectD.model.dto.SimDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface SimService {
    Page<SimDto> findAll(String status, String operator, String address, String equipment, Pageable pageable);
    SimDto findById(Long id);
    SimDto createSim(SimDto simDto);
    SimDto updateSim(Long id, SimDto simDto);
    void deleteSim(Long id);
}
