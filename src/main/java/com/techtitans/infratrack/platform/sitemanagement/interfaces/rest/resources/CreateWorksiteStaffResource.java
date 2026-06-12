package com.techtitans.infratrack.platform.sitemanagement.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CreateWorksiteStaffRequest")
public record CreateWorksiteStaffResource(
        String fullName,
        String email,
        String phone,
        String licenseNumber,
        String status
) {
}
