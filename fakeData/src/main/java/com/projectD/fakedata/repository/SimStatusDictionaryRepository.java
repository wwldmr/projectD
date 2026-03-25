package com.projectD.fakedata.repository;

import com.projectD.fakedata.entity.SimStatusDictionary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SimStatusDictionaryRepository extends JpaRepository<SimStatusDictionary, Long> {
    Optional<SimStatusDictionary> findByCode(String code);
}
