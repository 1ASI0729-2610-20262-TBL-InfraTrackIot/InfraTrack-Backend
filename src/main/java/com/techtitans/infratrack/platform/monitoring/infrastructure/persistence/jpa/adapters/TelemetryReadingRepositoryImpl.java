package com.techtitans.infratrack.platform.monitoring.infrastructure.persistence.jpa.adapters;

import com.techtitans.infratrack.platform.monitoring.domain.model.aggregates.TelemetryReading;
import com.techtitans.infratrack.platform.monitoring.domain.repositories.TelemetryReadingRepository;
import com.techtitans.infratrack.platform.monitoring.infrastructure.persistence.jpa.assemblers.TelemetryReadingPersistenceAssembler;
import com.techtitans.infratrack.platform.monitoring.infrastructure.persistence.jpa.repositories.TelemetryReadingPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TelemetryReadingRepositoryImpl implements TelemetryReadingRepository {

    private final TelemetryReadingPersistenceRepository telemetryReadingPersistenceRepository;

    public TelemetryReadingRepositoryImpl(TelemetryReadingPersistenceRepository telemetryReadingPersistenceRepository) {
        this.telemetryReadingPersistenceRepository = telemetryReadingPersistenceRepository;
    }

    @Override
    public Optional<TelemetryReading> findById(Long id) {
        return telemetryReadingPersistenceRepository.findById(id)
                .map(TelemetryReadingPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<TelemetryReading> findAll() {
        return telemetryReadingPersistenceRepository.findAll().stream()
                .map(TelemetryReadingPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public TelemetryReading save(TelemetryReading reading) {
        var saved = telemetryReadingPersistenceRepository.save(
                TelemetryReadingPersistenceAssembler.toPersistenceFromDomain(reading));
        return TelemetryReadingPersistenceAssembler.toDomainFromPersistence(saved);
    }
}
