package com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.adapters;

import com.techtitans.infratrack.platform.sitemanagement.domain.repositories.WorksiteStaffAssignmentRepository;
import com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.entities.WorksiteStaffAssignmentPersistenceEntity;
import com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.repositories.WorksiteStaffAssignmentPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WorksiteStaffAssignmentRepositoryImpl implements WorksiteStaffAssignmentRepository {

    private final WorksiteStaffAssignmentPersistenceRepository repository;

    public WorksiteStaffAssignmentRepositoryImpl(WorksiteStaffAssignmentPersistenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Long> findWorksiteIdsByStaffId(Long staffId) {
        return repository.findByStaffId(staffId).stream()
                .map(WorksiteStaffAssignmentPersistenceEntity::getWorksiteId)
                .toList();
    }

    @Override
    public List<Long> findStaffIdsByWorksiteId(Long worksiteId) {
        return repository.findByWorksiteId(worksiteId).stream()
                .map(WorksiteStaffAssignmentPersistenceEntity::getStaffId)
                .toList();
    }

    @Override
    public int countByWorksiteId(Long worksiteId) {
        return repository.countByWorksiteId(worksiteId);
    }

    @Override
    public boolean existsByStaffIdAndWorksiteId(Long staffId, Long worksiteId) {
        return repository.existsByStaffIdAndWorksiteId(staffId, worksiteId);
    }

    @Override
    public void save(Long staffId, Long worksiteId) {
        if (repository.existsByStaffIdAndWorksiteId(staffId, worksiteId)) {
            return;
        }
        var entity = new WorksiteStaffAssignmentPersistenceEntity();
        entity.setStaffId(staffId);
        entity.setWorksiteId(worksiteId);
        repository.save(entity);
    }
}
