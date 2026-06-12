package com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.adapters;

import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.WorksiteStaff;
import com.techtitans.infratrack.platform.sitemanagement.domain.repositories.WorksiteStaffRepository;
import com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.assemblers.WorksiteStaffPersistenceAssembler;
import com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.repositories.WorksiteStaffPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WorksiteStaffRepositoryImpl implements WorksiteStaffRepository {

    private final WorksiteStaffPersistenceRepository worksiteStaffPersistenceRepository;

    public WorksiteStaffRepositoryImpl(WorksiteStaffPersistenceRepository worksiteStaffPersistenceRepository) {
        this.worksiteStaffPersistenceRepository = worksiteStaffPersistenceRepository;
    }

    @Override
    public Optional<WorksiteStaff> findById(Long id) {
        return worksiteStaffPersistenceRepository.findById(id).map(WorksiteStaffPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<WorksiteStaff> findAll() {
        return worksiteStaffPersistenceRepository.findAll().stream()
                .map(WorksiteStaffPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsById(Long id) {
        return worksiteStaffPersistenceRepository.existsById(id);
    }

    @Override
    public boolean existsByEmailIgnoreCase(String email) {
        return worksiteStaffPersistenceRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public WorksiteStaff save(WorksiteStaff staff) {
        var saved = worksiteStaffPersistenceRepository.save(WorksiteStaffPersistenceAssembler.toPersistenceFromDomain(staff));
        return WorksiteStaffPersistenceAssembler.toDomainFromPersistence(saved);
    }
}
