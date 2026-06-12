package com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.repositories;

import com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.entities.WorksiteStaffAssignmentPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorksiteStaffAssignmentPersistenceRepository
        extends JpaRepository<WorksiteStaffAssignmentPersistenceEntity, Long> {
    List<WorksiteStaffAssignmentPersistenceEntity> findByStaffId(Long staffId);
    List<WorksiteStaffAssignmentPersistenceEntity> findByWorksiteId(Long worksiteId);
    int countByWorksiteId(Long worksiteId);
    boolean existsByStaffIdAndWorksiteId(Long staffId, Long worksiteId);
}
