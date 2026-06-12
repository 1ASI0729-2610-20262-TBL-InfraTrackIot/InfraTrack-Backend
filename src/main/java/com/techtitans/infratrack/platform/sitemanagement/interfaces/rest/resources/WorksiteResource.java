package com.techtitans.infratrack.platform.sitemanagement.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "WorksiteResponse")
public record WorksiteResource(
        Long id,
        String name,
        String city,
        String type,
        String status,
        String address,
        String leadEngineer,
        int transportCount,
        int staffCount,
        Double latitude,
        Double longitude
) {
}
