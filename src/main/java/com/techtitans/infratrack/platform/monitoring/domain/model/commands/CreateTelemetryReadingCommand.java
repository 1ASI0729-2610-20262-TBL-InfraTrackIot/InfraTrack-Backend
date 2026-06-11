package com.techtitans.infratrack.platform.monitoring.domain.model.commands;

import java.time.Instant;

public record CreateTelemetryReadingCommand(
        Long nodeId,
        Double fuelLevel,
        Integer fuelLevelPct,
        Double latitude,
        Double longitude,
        Double engineHours,
        Double speedKmh,
        Boolean engineOn,
        Instant recordedAt
) {
}
