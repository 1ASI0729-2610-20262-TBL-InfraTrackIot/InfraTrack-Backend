package com.techtitans.infratrack.platform.fleet.domain.model.commands;

import com.techtitans.infratrack.platform.fleet.domain.model.valueobjects.MaintenanceServiceType;

import java.time.LocalDate;

public record CreateMaintenanceRecordCommand(
        Long machineryId,
        MaintenanceServiceType serviceType,
        String description,
        Double costPen,
        Double engineHoursAtService,
        LocalDate serviceDate,
        LocalDate nextServiceDate
) {
}
