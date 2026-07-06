package com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.repositories;

import com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.entities.MaintenanceRecordPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintenanceRecordPersistenceRepository extends JpaRepository<MaintenanceRecordPersistenceEntity, Long> {
    List<MaintenanceRecordPersistenceEntity> findByMachineryId(Long machineryId);
}
