package com.techtitans.infratrack.platform.sitemanagement.application.internal.commandservices;

import com.techtitans.infratrack.platform.shared.application.result.ApplicationError;
import com.techtitans.infratrack.platform.shared.application.result.Result;
import com.techtitans.infratrack.platform.sitemanagement.application.commandservices.WorksiteStaffCommandService;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.WorksiteStaff;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.commands.AssignStaffToWorksiteCommand;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.commands.CreateWorksiteStaffCommand;
import com.techtitans.infratrack.platform.sitemanagement.domain.repositories.WorksiteRepository;
import com.techtitans.infratrack.platform.sitemanagement.domain.repositories.WorksiteStaffAssignmentRepository;
import com.techtitans.infratrack.platform.sitemanagement.domain.repositories.WorksiteStaffRepository;
import org.springframework.stereotype.Service;

@Service
public class WorksiteStaffCommandServiceImpl implements WorksiteStaffCommandService {

    private final WorksiteRepository worksiteRepository;
    private final WorksiteStaffRepository worksiteStaffRepository;
    private final WorksiteStaffAssignmentRepository staffAssignmentRepository;

    public WorksiteStaffCommandServiceImpl(
            WorksiteRepository worksiteRepository,
            WorksiteStaffRepository worksiteStaffRepository,
            WorksiteStaffAssignmentRepository staffAssignmentRepository) {
        this.worksiteRepository = worksiteRepository;
        this.worksiteStaffRepository = worksiteStaffRepository;
        this.staffAssignmentRepository = staffAssignmentRepository;
    }

    @Override
    public Result<WorksiteStaff, ApplicationError> handle(CreateWorksiteStaffCommand command) {
        if (command.fullName() == null || command.fullName().isBlank()) {
            return Result.failure(ApplicationError.validationError("fullName", "Full name is required"));
        }
        if (command.email() == null || command.email().isBlank()) {
            return Result.failure(ApplicationError.validationError("email", "Email is required"));
        }
        if (worksiteStaffRepository.existsByEmailIgnoreCase(command.email().trim())) {
            return Result.failure(ApplicationError.conflict("Staff", "Email '%s' already exists".formatted(command.email())));
        }
        try {
            return Result.success(worksiteStaffRepository.save(new WorksiteStaff(command)));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-staff", e.getMessage()));
        }
    }

    @Override
    public Result<Long, ApplicationError> handle(AssignStaffToWorksiteCommand command) {
        if (!worksiteRepository.existsById(command.worksiteId())) {
            return Result.failure(ApplicationError.notFound("Worksite", command.worksiteId().toString()));
        }
        if (!worksiteStaffRepository.existsById(command.staffId())) {
            return Result.failure(ApplicationError.notFound("Staff", command.staffId().toString()));
        }
        try {
            staffAssignmentRepository.save(command.staffId(), command.worksiteId());
            return Result.success(command.staffId());
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("assign-staff", e.getMessage()));
        }
    }
}
