package com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.adapters;

import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.Worksite;
import com.techtitans.infratrack.platform.sitemanagement.domain.repositories.WorksiteRepository;
import com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.assemblers.WorksitePersistenceAssembler;
import com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.repositories.WorksitePersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WorksiteRepositoryImpl implements WorksiteRepository {

    private final WorksitePersistenceRepository worksitePersistenceRepository;

    public WorksiteRepositoryImpl(WorksitePersistenceRepository worksitePersistenceRepository) {
        this.worksitePersistenceRepository = worksitePersistenceRepository;
    }

    @Override
    public Optional<Worksite> findById(Long id) {
        return worksitePersistenceRepository.findById(id).map(WorksitePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Worksite> findAll() {
        return worksitePersistenceRepository.findAll().stream().map(WorksitePersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override
    public boolean existsById(Long id) {
        return worksitePersistenceRepository.existsById(id);
    }

    @Override
    public Worksite save(Worksite worksite) {
        var saved = worksitePersistenceRepository.save(WorksitePersistenceAssembler.toPersistenceFromDomain(worksite));
        return WorksitePersistenceAssembler.toDomainFromPersistence(saved);
    }
}
