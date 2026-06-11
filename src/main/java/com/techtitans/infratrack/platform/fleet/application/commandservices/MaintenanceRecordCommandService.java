package com.techtitans.infratrack.platform.fleet.application.commandservices;

import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.MaintenanceRecord;
import com.techtitans.infratrack.platform.fleet.domain.model.commands.CreateMaintenanceRecordCommand;
import com.techtitans.infratrack.platform.shared.application.result.ApplicationError;
import com.techtitans.infratrack.platform.shared.application.result.Result;

public interface MaintenanceRecordCommandService {
    Result<MaintenanceRecord, ApplicationError> handle(CreateMaintenanceRecordCommand command);
}
