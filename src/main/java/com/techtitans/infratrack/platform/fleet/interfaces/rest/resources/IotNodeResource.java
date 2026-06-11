package com.techtitans.infratrack.platform.fleet.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "IotNodeResponse")
public record IotNodeResource(
        Long id,
        Long machineryId,
        String nodeIdentifier,
        String firmwareVersion,
        Double batteryVoltage,
        String connectionStatus,
        String lastSeen
) {
}
