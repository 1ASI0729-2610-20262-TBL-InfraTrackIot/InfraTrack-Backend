package com.techtitans.infratrack.platform.sitemanagement.application.queryservices;

import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.WorksiteStaff;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.queries.GetAllWorksiteStaffQuery;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.queries.GetStaffForWorksiteQuery;

import java.util.List;
import java.util.Optional;

public interface WorksiteStaffQueryService {
    List<WorksiteStaff> handle(GetAllWorksiteStaffQuery query);
    List<WorksiteStaff> handle(GetStaffForWorksiteQuery query);
    Optional<WorksiteStaff> findById(Long staffId);
    List<Long> findAssignedWorksiteIds(Long staffId);
}
