package com.projectD.fakedata.repository;

import com.projectD.fakedata.entity.AppGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppGroupRepository extends JpaRepository<AppGroup, Long> {
    Optional<AppGroup> findByName(String name);
}
