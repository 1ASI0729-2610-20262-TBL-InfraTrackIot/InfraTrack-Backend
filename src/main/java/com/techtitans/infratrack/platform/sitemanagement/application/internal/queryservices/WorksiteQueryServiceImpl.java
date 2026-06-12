package com.techtitans.infratrack.platform.sitemanagement.application.internal.queryservices;

import com.techtitans.infratrack.platform.sitemanagement.application.queryservices.WorksiteQueryService;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.Worksite;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.WorksiteTransportAssignment;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.queries.GetAllWorksitesQuery;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.queries.GetTransportsForWorksiteQuery;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.queries.GetWorksiteByIdQuery;
import com.techtitans.infratrack.platform.sitemanagement.domain.repositories.WorksiteRepository;
import com.techtitans.infratrack.platform.sitemanagement.domain.repositories.WorksiteStaffAssignmentRepository;
import com.techtitans.infratrack.platform.sitemanagement.domain.repositories.WorksiteTransportAssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorksiteQueryServiceImpl implements WorksiteQueryService {

    private final WorksiteRepository worksiteRepository;
    private final WorksiteTransportAssignmentRepository transportAssignmentRepository;
    private final WorksiteStaffAssignmentRepository staffAssignmentRepository;

    public WorksiteQueryServiceImpl(
            WorksiteRepository worksiteRepository,
            WorksiteTransportAssignmentRepository transportAssignmentRepository,
            WorksiteStaffAssignmentRepository staffAssignmentRepository) {
        this.worksiteRepository = worksiteRepository;
        this.transportAssignmentRepository = transportAssignmentRepository;
        this.staffAssignmentRepository = staffAssignmentRepository;
    }

    @Override
    public List<Worksite> handle(GetAllWorksitesQuery query) {
        return worksiteRepository.findAll();
    }

    @Override
    public Optional<Worksite> handle(GetWorksiteByIdQuery query) {
        return worksiteRepository.findById(query.worksiteId());
    }

    @Override
    public List<WorksiteTransportAssignment> handle(GetTransportsForWorksiteQuery query) {
        return transportAssignmentRepository.findByWorksiteId(query.worksiteId());
    }

    @Override
    public int countTransports(Long worksiteId) {
        return transportAssignmentRepository.countByWorksiteId(worksiteId);
    }

    @Override
    public int countStaff(Long worksiteId) {
        return staffAssignmentRepository.countByWorksiteId(worksiteId);
    }
}
