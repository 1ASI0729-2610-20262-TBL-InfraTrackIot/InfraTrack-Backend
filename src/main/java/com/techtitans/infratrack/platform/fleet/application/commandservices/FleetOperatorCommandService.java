package com.techtitans.infratrack.platform.fleet.application.commandservices;

import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.FleetOperator;
import com.techtitans.infratrack.platform.fleet.domain.model.commands.CreateFleetOperatorCommand;
import com.techtitans.infratrack.platform.shared.application.result.ApplicationError;
import com.techtitans.infratrack.platform.shared.application.result.Result;

public interface FleetOperatorCommandService {
    Result<FleetOperator, ApplicationError> handle(CreateFleetOperatorCommand command);
}
