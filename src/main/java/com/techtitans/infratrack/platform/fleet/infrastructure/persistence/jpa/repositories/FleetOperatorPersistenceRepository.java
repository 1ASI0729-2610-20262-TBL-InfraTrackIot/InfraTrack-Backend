package com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.repositories;

import com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.entities.FleetOperatorPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FleetOperatorPersistenceRepository extends JpaRepository<FleetOperatorPersistenceEntity, Long> {
    boolean existsByLicenseNumber(String licenseNumber);
}
