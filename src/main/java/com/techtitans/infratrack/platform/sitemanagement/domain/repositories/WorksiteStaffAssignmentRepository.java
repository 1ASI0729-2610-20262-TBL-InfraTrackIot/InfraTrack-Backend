package com.techtitans.infratrack.platform.sitemanagement.domain.repositories;

import java.util.List;

public interface WorksiteStaffAssignmentRepository {
    List<Long> findWorksiteIdsByStaffId(Long staffId);
    List<Long> findStaffIdsByWorksiteId(Long worksiteId);
    int countByWorksiteId(Long worksiteId);
    boolean existsByStaffIdAndWorksiteId(Long staffId, Long worksiteId);
    void save(Long staffId, Long worksiteId);
}
