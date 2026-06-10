package com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.repositories;

import com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.entities.MachineryPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MachineryPersistenceRepository extends JpaRepository<MachineryPersistenceEntity, Long> {
    boolean existsByPlateNumber(String plateNumber);
    Optional<MachineryPersistenceEntity> findByPlateNumber(String plateNumber);
}
