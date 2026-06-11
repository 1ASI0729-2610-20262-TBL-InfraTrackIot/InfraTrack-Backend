package com.techtitans.infratrack.platform.monitoring.domain.repositories;

import com.techtitans.infratrack.platform.monitoring.domain.model.aggregates.FleetAlert;

import java.util.List;
import java.util.Optional;

public interface FleetAlertRepository {
    Optional<FleetAlert> findById(Long id);
    List<FleetAlert> findAll();
    FleetAlert save(FleetAlert alert);
}
