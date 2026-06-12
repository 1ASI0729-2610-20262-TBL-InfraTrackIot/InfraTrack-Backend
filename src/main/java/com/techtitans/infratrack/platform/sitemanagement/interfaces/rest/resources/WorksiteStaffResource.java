package com.techtitans.infratrack.platform.sitemanagement.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "WorksiteStaffResponse")
public record WorksiteStaffResource(
        Long id,
        String fullName,
        String email,
        String phone,
        String licenseNumber,
        String status,
        List<Long> assignedWorksiteIds,
        int alertsLast30Days,
        int drivingHoursWeek,
        String currentVehicle
) {
}
