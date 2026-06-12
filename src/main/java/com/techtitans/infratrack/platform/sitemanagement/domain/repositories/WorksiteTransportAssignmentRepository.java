package com.techtitans.infratrack.platform.sitemanagement.domain.repositories;

import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.WorksiteTransportAssignment;

import java.util.List;
import java.util.Optional;

public interface WorksiteTransportAssignmentRepository {
    Optional<WorksiteTransportAssignment> findById(Long id);
    List<WorksiteTransportAssignment> findByWorksiteId(Long worksiteId);
    Optional<WorksiteTransportAssignment> findByMachineryId(Long machineryId);
    int countByWorksiteId(Long worksiteId);
    WorksiteTransportAssignment save(WorksiteTransportAssignment assignment);
    void deleteByMachineryId(Long machineryId);
}
