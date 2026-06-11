package com.techtitans.infratrack.platform.fleet.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CreateMaintenanceRecordRequest")
public record CreateMaintenanceRecordResource(
        Long machineryId,
        String serviceType,
        String description,
        Double costPen,
        Double engineHoursAtService,
        String serviceDate,
        String nextServiceDate
) {
}
