package com.techtitans.infratrack.platform.fleet.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UpdateMachineryRequest")
public record UpdateMachineryResource(
        Long operatorId,
        String currentStatus
) {
}
