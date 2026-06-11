package com.techtitans.infratrack.platform.fleet.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "OperatorResponse")
public record OperatorResource(
        Long id,
        Long userId,
        String fullName,
        String licenseNumber,
        String phone,
        String status
) {
}
