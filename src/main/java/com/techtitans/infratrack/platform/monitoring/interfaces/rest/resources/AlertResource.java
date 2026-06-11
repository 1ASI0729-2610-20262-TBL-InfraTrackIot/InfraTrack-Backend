package com.techtitans.infratrack.platform.monitoring.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AlertResponse")
public record AlertResource(
        Long id,
        Long machineryId,
        String type,
        String severity,
        String description,
        Boolean isAcknowledged,
        String timestamp
) {
}
