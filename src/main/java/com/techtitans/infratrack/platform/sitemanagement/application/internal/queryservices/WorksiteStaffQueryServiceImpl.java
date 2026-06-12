package com.techtitans.infratrack.platform.sitemanagement.application.internal.queryservices;

import com.techtitans.infratrack.platform.sitemanagement.application.queryservices.WorksiteStaffQueryService;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.WorksiteStaff;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.queries.GetAllWorksiteStaffQuery;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.queries.GetStaffForWorksiteQuery;
import com.techtitans.infratrack.platform.sitemanagement.domain.repositories.WorksiteStaffAssignmentRepository;
import com.techtitans.infratrack.platform.sitemanagement.domain.repositories.WorksiteStaffRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorksiteStaffQueryServiceImpl implements WorksiteStaffQueryService {

    private final WorksiteStaffRepository worksiteStaffRepository;
    private final WorksiteStaffAssignmentRepository staffAssignmentRepository;

    public WorksiteStaffQueryServiceImpl(
            WorksiteStaffRepository worksiteStaffRepository,
            WorksiteStaffAssignmentRepository staffAssignmentRepository) {
        this.worksiteStaffRepository = worksiteStaffRepository;
        this.staffAssignmentRepository = staffAssignmentRepository;
    }

    @Override
    public List<WorksiteStaff> handle(GetAllWorksiteStaffQuery query) {
        return worksiteStaffRepository.findAll();
    }

    @Override
    public List<WorksiteStaff> handle(GetStaffForWorksiteQuery query) {
        var staffIds = staffAssignmentRepository.findStaffIdsByWorksiteId(query.worksiteId());
        return worksiteStaffRepository.findAll().stream()
                .filter(staff -> staffIds.contains(staff.getId()))
                .toList();
    }

    @Override
    public Optional<WorksiteStaff> findById(Long staffId) {
        return worksiteStaffRepository.findById(staffId);
    }

    @Override
    public List<Long> findAssignedWorksiteIds(Long staffId) {
        return staffAssignmentRepository.findWorksiteIdsByStaffId(staffId);
    }
}
