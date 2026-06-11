package com.techtitans.infratrack.platform.fleet.application.commandservices;

import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.Machinery;
import com.techtitans.infratrack.platform.fleet.domain.model.commands.CreateMachineryCommand;
import com.techtitans.infratrack.platform.fleet.domain.model.commands.UpdateMachineryCommand;
import com.techtitans.infratrack.platform.shared.application.result.ApplicationError;
import com.techtitans.infratrack.platform.shared.application.result.Result;

public interface MachineryCommandService {
    Result<Machinery, ApplicationError> handle(CreateMachineryCommand command);
    Result<Machinery, ApplicationError> handle(UpdateMachineryCommand command);
}
