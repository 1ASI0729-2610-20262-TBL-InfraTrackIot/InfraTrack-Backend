package com.techtitans.infratrack.platform.monitoring.application.commandservices;

import com.techtitans.infratrack.platform.monitoring.domain.model.aggregates.TelemetryReading;
import com.techtitans.infratrack.platform.monitoring.domain.model.commands.CreateTelemetryReadingCommand;
import com.techtitans.infratrack.platform.shared.application.result.ApplicationError;
import com.techtitans.infratrack.platform.shared.application.result.Result;

public interface TelemetryReadingCommandService {
    Result<TelemetryReading, ApplicationError> handle(CreateTelemetryReadingCommand command);
}
