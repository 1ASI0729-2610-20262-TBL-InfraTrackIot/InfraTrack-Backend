package com.techtitans.infratrack.platform.sitemanagement.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "WorksiteTransportResponse")
public record WorksiteTransportResource(
        Long id,
        Long worksiteId,
        String plateNumber,
        String model,
        String brand,
        String iotNodeId,
        String gpsLabel,
        int fuelLevelPercent,
        String status
) {
}
