package com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.repositories;

import com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.entities.MaintenanceRecordPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceRecordPersistenceRepository extends JpaRepository<MaintenanceRecordPersistenceEntity, Long> {
}
