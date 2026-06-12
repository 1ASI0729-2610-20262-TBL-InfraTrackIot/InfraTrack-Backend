package com.techtitans.infratrack.platform.sitemanagement.application.commandservices;

import com.techtitans.infratrack.platform.shared.application.result.ApplicationError;
import com.techtitans.infratrack.platform.shared.application.result.Result;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.Worksite;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.WorksiteTransportAssignment;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.commands.AssignTransportToWorksiteCommand;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.commands.CreateWorksiteCommand;

public interface WorksiteCommandService {
    Result<Worksite, ApplicationError> handle(CreateWorksiteCommand command);
    Result<WorksiteTransportAssignment, ApplicationError> handle(AssignTransportToWorksiteCommand command);
}
