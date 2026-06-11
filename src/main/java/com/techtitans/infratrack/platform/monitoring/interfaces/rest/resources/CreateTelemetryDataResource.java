package com.techtitans.infratrack.platform.monitoring.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CreateTelemetryDataRequest")
public record CreateTelemetryDataResource(
        Long nodeId,
        Double fuelLevel,
        Integer fuelLevelPct,
        Double latitude,
        Double longitude,
        Double engineHours,
        Double speedKmh,
        Boolean engineOn,
        String recordedAt
) {
}
