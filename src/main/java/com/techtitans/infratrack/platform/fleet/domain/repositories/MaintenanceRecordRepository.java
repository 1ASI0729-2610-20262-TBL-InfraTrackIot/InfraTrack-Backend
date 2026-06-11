package com.techtitans.infratrack.platform.fleet.domain.repositories;

import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.MaintenanceRecord;

import java.util.List;
import java.util.Optional;

public interface MaintenanceRecordRepository {
    Optional<MaintenanceRecord> findById(Long id);
    List<MaintenanceRecord> findAll();
    boolean existsById(Long id);
    MaintenanceRecord save(MaintenanceRecord record);
}
