package com.techtitans.infratrack.platform.fleet.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MaintenanceRecordResponse")
public record MaintenanceRecordResource(
        Long id,
        Long machineryId,
        String serviceType,
        String description,
        Double costPen,
        Double engineHoursAtService,
        String serviceDate,
        String nextServiceDate
) {
}
