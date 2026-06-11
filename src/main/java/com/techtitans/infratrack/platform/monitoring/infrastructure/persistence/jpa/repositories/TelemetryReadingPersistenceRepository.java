package com.techtitans.infratrack.platform.monitoring.infrastructure.persistence.jpa.repositories;

import com.techtitans.infratrack.platform.monitoring.infrastructure.persistence.jpa.entities.TelemetryReadingPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelemetryReadingPersistenceRepository extends JpaRepository<TelemetryReadingPersistenceEntity, Long> {
}
