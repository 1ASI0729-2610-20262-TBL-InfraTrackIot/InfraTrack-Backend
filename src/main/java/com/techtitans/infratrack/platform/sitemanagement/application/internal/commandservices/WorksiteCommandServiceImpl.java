package com.techtitans.infratrack.platform.sitemanagement.application.internal.commandservices;

import com.techtitans.infratrack.platform.shared.application.result.ApplicationError;
import com.techtitans.infratrack.platform.shared.application.result.Result;
import com.techtitans.infratrack.platform.sitemanagement.application.commandservices.WorksiteCommandService;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.Worksite;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.commands.CreateWorksiteCommand;
import com.techtitans.infratrack.platform.sitemanagement.domain.repositories.WorksiteRepository;
import org.springframework.stereotype.Service;

/**
 * v0.17.0 slice — create worksite only.
 */
@Service
public class WorksiteCommandServiceImpl implements WorksiteCommandService {

    private final WorksiteRepository worksiteRepository;

    public WorksiteCommandServiceImpl(WorksiteRepository worksiteRepository) {
        this.worksiteRepository = worksiteRepository;
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
}
