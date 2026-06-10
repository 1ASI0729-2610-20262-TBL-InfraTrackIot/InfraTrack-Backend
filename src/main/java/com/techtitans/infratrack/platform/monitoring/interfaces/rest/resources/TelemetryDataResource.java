package com.techtitans.infratrack.platform.monitoring.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TelemetryDataResponse")
public record TelemetryDataResource(
        Long id,
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
