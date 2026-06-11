package com.techtitans.infratrack.platform.fleet.domain.model.commands;

import com.techtitans.infratrack.platform.fleet.domain.model.valueobjects.ConnectionStatus;

import java.time.Instant;

public record CreateIotNodeCommand(
        Long machineryId,
        String nodeIdentifier,
        String firmwareVersion,
        Double batteryVoltage,
        ConnectionStatus connectionStatus,
        Instant lastSeen
) {
}
