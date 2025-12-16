package com.projectD.admin.repository;

import com.projectD.model.entity.SimCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SimRepository extends JpaRepository<SimCard, Long> {
    Optional<SimCard> findByIccid(String iccid);
}
