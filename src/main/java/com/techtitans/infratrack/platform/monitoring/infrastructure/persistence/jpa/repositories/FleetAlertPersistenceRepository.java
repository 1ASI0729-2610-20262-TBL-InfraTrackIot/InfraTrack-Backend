package com.techtitans.infratrack.platform.monitoring.infrastructure.persistence.jpa.repositories;

import com.techtitans.infratrack.platform.monitoring.infrastructure.persistence.jpa.entities.FleetAlertPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FleetAlertPersistenceRepository extends JpaRepository<FleetAlertPersistenceEntity, Long> {
}
