package com.projectD.fakedata.repository;

import com.projectD.fakedata.entity.MobileOperator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MobileOperatorRepository extends JpaRepository<MobileOperator, Long> {
    Optional<MobileOperator> findByName(String name);
}
