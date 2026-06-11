package com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.adapters;

import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.MaintenanceRecord;
import com.techtitans.infratrack.platform.fleet.domain.repositories.MaintenanceRecordRepository;
import com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.assemblers.MaintenanceRecordPersistenceAssembler;
import com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.repositories.MaintenanceRecordPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MaintenanceRecordRepositoryImpl implements MaintenanceRecordRepository {

    private final MaintenanceRecordPersistenceRepository maintenanceRecordPersistenceRepository;

    public MaintenanceRecordRepositoryImpl(MaintenanceRecordPersistenceRepository maintenanceRecordPersistenceRepository) {
        this.maintenanceRecordPersistenceRepository = maintenanceRecordPersistenceRepository;
    }

    @Override
    public Optional<MaintenanceRecord> findById(Long id) {
        return maintenanceRecordPersistenceRepository.findById(id).map(MaintenanceRecordPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<MaintenanceRecord> findAll() {
        return maintenanceRecordPersistenceRepository.findAll().stream().map(MaintenanceRecordPersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override
    public boolean existsById(Long id) {
        return maintenanceRecordPersistenceRepository.existsById(id);
    }

    @Override
    public MaintenanceRecord save(MaintenanceRecord record) {
        var saved = maintenanceRecordPersistenceRepository.save(MaintenanceRecordPersistenceAssembler.toPersistenceFromDomain(record));
        return MaintenanceRecordPersistenceAssembler.toDomainFromPersistence(saved);
    }
}
