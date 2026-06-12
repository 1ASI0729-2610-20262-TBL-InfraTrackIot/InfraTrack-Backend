package com.techtitans.infratrack.platform.sitemanagement.application.internal.commandservices;

import com.techtitans.infratrack.platform.shared.application.result.ApplicationError;
import com.techtitans.infratrack.platform.shared.application.result.Result;
import com.techtitans.infratrack.platform.sitemanagement.application.commandservices.WorksiteCommandService;
import com.techtitans.infratrack.platform.sitemanagement.application.internal.outboundservices.acl.SiteManagementExternalFleetService;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.Worksite;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.WorksiteTransportAssignment;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.commands.AssignTransportToWorksiteCommand;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.commands.CreateWorksiteCommand;
import com.techtitans.infratrack.platform.sitemanagement.domain.repositories.WorksiteRepository;
import com.techtitans.infratrack.platform.sitemanagement.domain.repositories.WorksiteTransportAssignmentRepository;
import org.springframework.stereotype.Service;

@Service
public class WorksiteCommandServiceImpl implements WorksiteCommandService {

    private final WorksiteRepository worksiteRepository;
    private final WorksiteTransportAssignmentRepository transportAssignmentRepository;
    private final SiteManagementExternalFleetService externalFleetService;

    public WorksiteCommandServiceImpl(
            WorksiteRepository worksiteRepository,
            WorksiteTransportAssignmentRepository transportAssignmentRepository,
            SiteManagementExternalFleetService externalFleetService) {
        this.worksiteRepository = worksiteRepository;
        this.transportAssignmentRepository = transportAssignmentRepository;
        this.externalFleetService = externalFleetService;
    }

    @Override
    public Result<Worksite, ApplicationError> handle(CreateWorksiteCommand command) {
        if (command.name() == null || command.name().isBlank()) {
            return Result.failure(ApplicationError.validationError("Worksite", "name is required"));
        }
        try {
            return Result.success(worksiteRepository.save(new Worksite(command)));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-worksite", e.getMessage()));
        }
    }

    @Override
    public Result<WorksiteTransportAssignment, ApplicationError> handle(AssignTransportToWorksiteCommand command) {
        if (!worksiteRepository.existsById(command.worksiteId())) {
            return Result.failure(ApplicationError.notFound("Worksite", command.worksiteId().toString()));
        }
        if (!externalFleetService.machineryExists(command.transportId())) {
            return Result.failure(ApplicationError.notFound("Transport", command.transportId().toString()));
        }
        try {
            transportAssignmentRepository.findByMachineryId(command.transportId()).ifPresent(existing -> {
                if (!existing.getWorksiteId().equals(command.worksiteId())) {
                    transportAssignmentRepository.deleteByMachineryId(command.transportId());
                }
            });
            var existingOnWorksite = transportAssignmentRepository.findByMachineryId(command.transportId());
            if (existingOnWorksite.isPresent() && existingOnWorksite.get().getWorksiteId().equals(command.worksiteId())) {
                var current = existingOnWorksite.get();
                if (command.gpsLabel() != null && !command.gpsLabel().isBlank()) {
                    current.setGpsLabel(command.gpsLabel());
                    return Result.success(transportAssignmentRepository.save(current));
                }
                return Result.success(current);
            }
            return Result.success(transportAssignmentRepository.save(new WorksiteTransportAssignment(command)));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("assign-transport", e.getMessage()));
        }
    }
}
