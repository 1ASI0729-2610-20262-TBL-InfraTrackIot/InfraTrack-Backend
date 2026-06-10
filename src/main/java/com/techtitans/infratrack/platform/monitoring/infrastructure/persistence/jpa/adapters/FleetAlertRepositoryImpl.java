package com.techtitans.infratrack.platform.monitoring.infrastructure.persistence.jpa.adapters;

import com.techtitans.infratrack.platform.monitoring.domain.model.aggregates.FleetAlert;
import com.techtitans.infratrack.platform.monitoring.domain.repositories.FleetAlertRepository;
import com.techtitans.infratrack.platform.monitoring.infrastructure.persistence.jpa.assemblers.FleetAlertPersistenceAssembler;
import com.techtitans.infratrack.platform.monitoring.infrastructure.persistence.jpa.repositories.FleetAlertPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FleetAlertRepositoryImpl implements FleetAlertRepository {

    private final FleetAlertPersistenceRepository fleetAlertPersistenceRepository;

    public FleetAlertRepositoryImpl(FleetAlertPersistenceRepository fleetAlertPersistenceRepository) {
        this.fleetAlertPersistenceRepository = fleetAlertPersistenceRepository;
    }

    @Override
    public Optional<FleetAlert> findById(Long id) {
        return fleetAlertPersistenceRepository.findById(id)
                .map(FleetAlertPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<FleetAlert> findAll() {
        return fleetAlertPersistenceRepository.findAll().stream()
                .map(FleetAlertPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public FleetAlert save(FleetAlert alert) {
        var saved = fleetAlertPersistenceRepository.save(
                FleetAlertPersistenceAssembler.toPersistenceFromDomain(alert));
        return FleetAlertPersistenceAssembler.toDomainFromPersistence(saved);
    }
}
