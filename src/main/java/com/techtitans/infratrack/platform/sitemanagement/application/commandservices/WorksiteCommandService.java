package com.techtitans.infratrack.platform.sitemanagement.application.commandservices;

import com.techtitans.infratrack.platform.shared.application.result.ApplicationError;
import com.techtitans.infratrack.platform.shared.application.result.Result;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.Worksite;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.commands.CreateWorksiteCommand;

/**
 * v0.17.0 slice — create worksite only (assign transport in v0.19.0).
 */
public interface WorksiteCommandService {
    Result<Worksite, ApplicationError> handle(CreateWorksiteCommand command);
}
