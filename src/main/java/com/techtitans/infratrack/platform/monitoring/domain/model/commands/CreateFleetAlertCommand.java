package com.techtitans.infratrack.platform.monitoring.domain.model.commands;

import com.techtitans.infratrack.platform.monitoring.domain.model.valueobjects.AlertSeverity;
import com.techtitans.infratrack.platform.monitoring.domain.model.valueobjects.AlertType;

import java.time.Instant;

public record CreateFleetAlertCommand(
        Long machineryId,
        AlertType type,
        AlertSeverity severity,
        String description,
        boolean isAcknowledged,
        Instant timestamp
) {
}
