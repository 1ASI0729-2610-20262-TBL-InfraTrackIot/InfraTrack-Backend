package com.techtitans.infratrack.platform.monitoring.application.commandservices;

import com.techtitans.infratrack.platform.monitoring.domain.model.aggregates.FleetAlert;
import com.techtitans.infratrack.platform.monitoring.domain.model.commands.AcknowledgeFleetAlertCommand;
import com.techtitans.infratrack.platform.monitoring.domain.model.commands.CreateFleetAlertCommand;
import com.techtitans.infratrack.platform.shared.application.result.ApplicationError;
import com.techtitans.infratrack.platform.shared.application.result.Result;

public interface FleetAlertCommandService {
    Result<FleetAlert, ApplicationError> handle(CreateFleetAlertCommand command);
    Result<FleetAlert, ApplicationError> handle(AcknowledgeFleetAlertCommand command);
}
