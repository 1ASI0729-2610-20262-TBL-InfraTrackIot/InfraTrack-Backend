package com.techtitans.infratrack.platform.monitoring.domain.repositories;

import com.techtitans.infratrack.platform.monitoring.domain.model.aggregates.TelemetryReading;

import java.util.List;
import java.util.Optional;

public interface TelemetryReadingRepository {
    Optional<TelemetryReading> findById(Long id);
    List<TelemetryReading> findAll();
    TelemetryReading save(TelemetryReading reading);
}
