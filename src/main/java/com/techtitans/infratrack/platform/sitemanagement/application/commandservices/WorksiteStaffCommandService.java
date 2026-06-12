package com.techtitans.infratrack.platform.sitemanagement.application.commandservices;

import com.techtitans.infratrack.platform.shared.application.result.ApplicationError;
import com.techtitans.infratrack.platform.shared.application.result.Result;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.WorksiteStaff;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.commands.AssignStaffToWorksiteCommand;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.commands.CreateWorksiteStaffCommand;

public interface WorksiteStaffCommandService {
    Result<WorksiteStaff, ApplicationError> handle(CreateWorksiteStaffCommand command);

    Result<Long, ApplicationError> handle(AssignStaffToWorksiteCommand command);
}
