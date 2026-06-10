package com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.adapters;

import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.Machinery;
import com.techtitans.infratrack.platform.fleet.domain.repositories.MachineryRepository;
import com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.assemblers.MachineryPersistenceAssembler;
import com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.repositories.MachineryPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MachineryRepositoryImpl implements MachineryRepository {

    private final MachineryPersistenceRepository machineryPersistenceRepository;

    public MachineryRepositoryImpl(MachineryPersistenceRepository machineryPersistenceRepository) {
        this.machineryPersistenceRepository = machineryPersistenceRepository;
    }

    @Override
    public Optional<Machinery> findById(Long id) {
        return machineryPersistenceRepository.findById(id).map(MachineryPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Machinery> findAll() {
        return machineryPersistenceRepository.findAll().stream().map(MachineryPersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override
    public boolean existsById(Long id) {
        return machineryPersistenceRepository.existsById(id);
    }

    @Override
    public boolean existsByPlateNumber(String plateNumber) {
        return machineryPersistenceRepository.existsByPlateNumber(plateNumber);
    }

    @Override
    public Optional<Machinery> findByPlateNumber(String plateNumber) {
        return machineryPersistenceRepository.findByPlateNumber(plateNumber)
                .map(MachineryPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Machinery save(Machinery machinery) {
        var saved = machineryPersistenceRepository.save(MachineryPersistenceAssembler.toPersistenceFromDomain(machinery));
        return MachineryPersistenceAssembler.toDomainFromPersistence(saved);
    }
}
