package com.techtitans.infratrack.platform.sitemanagement.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CreateWorksiteRequest")
public record CreateWorksiteResource(
        String name,
        String city,
        String type,
        String status,
        String address,
        String leadEngineer,
        Double latitude,
        Double longitude
) {
}
