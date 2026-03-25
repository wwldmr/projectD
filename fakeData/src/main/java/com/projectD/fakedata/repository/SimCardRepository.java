package com.projectD.fakedata.repository;

import com.projectD.fakedata.entity.SimCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimCardRepository extends JpaRepository<SimCard, Long> {
    boolean existsByIccid(String iccid);
}
