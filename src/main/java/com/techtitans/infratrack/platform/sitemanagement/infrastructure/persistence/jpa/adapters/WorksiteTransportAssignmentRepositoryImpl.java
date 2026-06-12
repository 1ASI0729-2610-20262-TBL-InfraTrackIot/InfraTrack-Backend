package com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.adapters;

import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.WorksiteTransportAssignment;
import com.techtitans.infratrack.platform.sitemanagement.domain.repositories.WorksiteTransportAssignmentRepository;
import com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.assemblers.WorksiteTransportAssignmentPersistenceAssembler;
import com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.repositories.WorksiteTransportAssignmentPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WorksiteTransportAssignmentRepositoryImpl implements WorksiteTransportAssignmentRepository {

    private final WorksiteTransportAssignmentPersistenceRepository repository;

    public WorksiteTransportAssignmentRepositoryImpl(WorksiteTransportAssignmentPersistenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<WorksiteTransportAssignment> findById(Long id) {
        return repository.findById(id).map(WorksiteTransportAssignmentPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<WorksiteTransportAssignment> findByWorksiteId(Long worksiteId) {
        return repository.findByWorksiteId(worksiteId).stream()
                .map(WorksiteTransportAssignmentPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Optional<WorksiteTransportAssignment> findByMachineryId(Long machineryId) {
        return repository.findByMachineryId(machineryId)
                .map(WorksiteTransportAssignmentPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public int countByWorksiteId(Long worksiteId) {
        return repository.countByWorksiteId(worksiteId);
    }

    @Override
    public WorksiteTransportAssignment save(WorksiteTransportAssignment assignment) {
        var saved = repository.save(WorksiteTransportAssignmentPersistenceAssembler.toPersistenceFromDomain(assignment));
        return WorksiteTransportAssignmentPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public void deleteByMachineryId(Long machineryId) {
        repository.deleteByMachineryId(machineryId);
    }
}
