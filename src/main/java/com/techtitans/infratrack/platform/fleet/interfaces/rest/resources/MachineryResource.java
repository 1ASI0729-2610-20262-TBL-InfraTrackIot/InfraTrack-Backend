package com.techtitans.infratrack.platform.fleet.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MachineryResponse")
public record MachineryResource(
        Long id,
        Long operatorId,
        String plateNumber,
        String model,
        String brand,
        String fuelType,
        Integer tankCapacityLiters,
        String currentStatus,
        String imageUrl,
        String createdAt
) {
}
