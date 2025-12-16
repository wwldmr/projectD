package com.projectD.admin.service.Impl;

import com.projectD.admin.Exception;
import com.projectD.admin.repository.SimRepository;
import com.projectD.admin.service.SimService;
import com.projectD.model.dto.SimDto;
import com.projectD.model.entity.SimCard;
import com.projectD.model.mapper.SimMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SimServiceImplementation implements SimService {

    private final SimRepository simRepository;

    @Override
    public Page<SimDto> findAll(String status, String operator, String address, String equipment, Pageable pageable) {
        return simRepository.findAll(pageable).map(SimMapper::mapToSimDto);
    }

    @Override
    public SimDto findById(Long id) {
        SimCard simCard = simRepository.findById(id)
                .orElseThrow(() -> new com.projectD.admin.Exception("The SIM card does not exist with the specified ID : " + id));

        return SimMapper.mapToSimDto(simCard);
    }

    @Override
    public SimDto createSim(SimDto simDto) {
        SimCard simCard = new SimCard();

        SimCard saved = simRepository.save(simCard);
        return SimMapper.mapToSimDto(saved);
    }

    @Override
    public SimDto updateSim(Long id, SimDto simDto) {
        SimCard existing = simRepository.findById(id)
                .orElseThrow(() -> new com.projectD.admin.Exception("The SIM card does not exist with the specified ID : " + id));

        SimCard updated = simRepository.save(existing);
        return SimMapper.mapToSimDto(updated);
    }

    @Override
    public void deleteSim(Long id) {
        if (!simRepository.existsById(id)) {
            throw new Exception("The SIM card does not exist with the specified ID : " + id);
        }
        simRepository.deleteById(id);
    }
}
