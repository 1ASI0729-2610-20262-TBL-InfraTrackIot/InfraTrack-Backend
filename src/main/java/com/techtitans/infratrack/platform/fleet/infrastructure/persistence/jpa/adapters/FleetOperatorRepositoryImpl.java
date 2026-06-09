package com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.adapters;

import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.FleetOperator;
import com.techtitans.infratrack.platform.fleet.domain.repositories.FleetOperatorRepository;
import com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.assemblers.FleetOperatorPersistenceAssembler;
import com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.repositories.FleetOperatorPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FleetOperatorRepositoryImpl implements FleetOperatorRepository {

    private final FleetOperatorPersistenceRepository fleetOperatorPersistenceRepository;

    public FleetOperatorRepositoryImpl(FleetOperatorPersistenceRepository fleetOperatorPersistenceRepository) {
        this.fleetOperatorPersistenceRepository = fleetOperatorPersistenceRepository;
    }

    @Override
    public Optional<FleetOperator> findById(Long id) {
        return fleetOperatorPersistenceRepository.findById(id).map(FleetOperatorPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<FleetOperator> findAll() {
        return fleetOperatorPersistenceRepository.findAll().stream().map(FleetOperatorPersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override
    public boolean existsById(Long id) {
        return fleetOperatorPersistenceRepository.existsById(id);
    }

    @Override
    public boolean existsByLicenseNumber(String licenseNumber) {
        return fleetOperatorPersistenceRepository.existsByLicenseNumber(licenseNumber);
    }

    @Override
    public FleetOperator save(FleetOperator operator) {
        var saved = fleetOperatorPersistenceRepository.save(FleetOperatorPersistenceAssembler.toPersistenceFromDomain(operator));
        return FleetOperatorPersistenceAssembler.toDomainFromPersistence(saved);
    }
}
