package com.techtitans.infratrack.platform.sitemanagement.application.queryservices;

import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.Worksite;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.WorksiteTransportAssignment;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.queries.GetAllWorksitesQuery;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.queries.GetTransportsForWorksiteQuery;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.queries.GetWorksiteByIdQuery;

import java.util.List;
import java.util.Optional;

public interface WorksiteQueryService {
    List<Worksite> handle(GetAllWorksitesQuery query);
    Optional<Worksite> handle(GetWorksiteByIdQuery query);
    List<WorksiteTransportAssignment> handle(GetTransportsForWorksiteQuery query);
    int countTransports(Long worksiteId);
    int countStaff(Long worksiteId);
}
