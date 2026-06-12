package com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.repositories;

import com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.entities.WorksiteTransportAssignmentPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorksiteTransportAssignmentPersistenceRepository
        extends JpaRepository<WorksiteTransportAssignmentPersistenceEntity, Long> {
    List<WorksiteTransportAssignmentPersistenceEntity> findByWorksiteId(Long worksiteId);
    Optional<WorksiteTransportAssignmentPersistenceEntity> findByMachineryId(Long machineryId);
    int countByWorksiteId(Long worksiteId);
    void deleteByMachineryId(Long machineryId);
}
