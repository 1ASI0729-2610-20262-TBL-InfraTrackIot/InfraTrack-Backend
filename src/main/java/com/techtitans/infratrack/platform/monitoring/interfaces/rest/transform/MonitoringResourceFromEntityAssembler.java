package com.techtitans.infratrack.platform.monitoring.interfaces.rest.transform;

import com.techtitans.infratrack.platform.monitoring.domain.model.aggregates.FleetAlert;
import com.techtitans.infratrack.platform.monitoring.domain.model.aggregates.TelemetryReading;
import com.techtitans.infratrack.platform.monitoring.domain.model.commands.CreateFleetAlertCommand;
import com.techtitans.infratrack.platform.monitoring.domain.model.commands.CreateTelemetryReadingCommand;
import com.techtitans.infratrack.platform.monitoring.domain.model.valueobjects.AlertSeverity;
import com.techtitans.infratrack.platform.monitoring.domain.model.valueobjects.AlertType;
import com.techtitans.infratrack.platform.monitoring.interfaces.rest.resources.AlertResource;
import com.techtitans.infratrack.platform.monitoring.interfaces.rest.resources.CreateAlertResource;
import com.techtitans.infratrack.platform.monitoring.interfaces.rest.resources.CreateTelemetryDataResource;
import com.techtitans.infratrack.platform.monitoring.interfaces.rest.resources.TelemetryDataResource;

import java.time.Instant;

public final class MonitoringResourceFromEntityAssembler {

    private MonitoringResourceFromEntityAssembler() {
    }

    public static TelemetryDataResource toTelemetryResourceFromEntity(TelemetryReading reading) {
        return new TelemetryDataResource(
                reading.getId(),
                reading.getNodeId(),
                reading.getFuelLevel(),
                reading.getFuelLevelPct(),
                reading.getLatitude(),
                reading.getLongitude(),
                reading.getEngineHours(),
                reading.getSpeedKmh(),
                reading.getEngineOn(),
                reading.getRecordedAt() != null ? reading.getRecordedAt().toString() : Instant.now().toString()
        );
    }

    public static AlertResource toAlertResourceFromEntity(FleetAlert alert) {
        return new AlertResource(
                alert.getId(),
                alert.getMachineryId(),
                alert.getType().toApiValue(),
                alert.getSeverity().toApiValue(),
                alert.getDescription(),
                alert.isAcknowledged(),
                alert.getTimestamp() != null ? alert.getTimestamp().toString() : Instant.now().toString()
        );
    }

    public static CreateTelemetryReadingCommand toCreateTelemetryCommandFromResource(CreateTelemetryDataResource resource) {
        Instant recordedAt = resource.recordedAt() != null && !resource.recordedAt().isBlank()
                ? Instant.parse(resource.recordedAt())
                : Instant.now();
        return new CreateTelemetryReadingCommand(
                resource.nodeId(),
                resource.fuelLevel(),
                resource.fuelLevelPct(),
                resource.latitude(),
                resource.longitude(),
                resource.engineHours(),
                resource.speedKmh(),
                resource.engineOn() != null ? resource.engineOn() : true,
                recordedAt
        );
    }

    public static CreateFleetAlertCommand toCreateAlertCommandFromResource(
            Long machineryId,
            CreateAlertResource resource) {
        Instant timestamp = resource.timestamp() != null && !resource.timestamp().isBlank()
                ? Instant.parse(resource.timestamp())
                : Instant.now();
        return new CreateFleetAlertCommand(
                machineryId,
                AlertType.fromApiValue(resource.type()),
                AlertSeverity.fromApiValue(resource.severity()),
                resource.description(),
                Boolean.TRUE.equals(resource.isAcknowledged()),
                timestamp
        );
    }
}