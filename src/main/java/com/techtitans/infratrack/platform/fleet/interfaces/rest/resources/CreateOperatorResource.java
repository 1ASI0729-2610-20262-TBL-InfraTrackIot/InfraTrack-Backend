package com.techtitans.infratrack.platform.fleet.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CreateOperatorRequest")
public record CreateOperatorResource(
        Long userId,
        String fullName,
        String licenseNumber,
        String phone,
        String status
) {
}
