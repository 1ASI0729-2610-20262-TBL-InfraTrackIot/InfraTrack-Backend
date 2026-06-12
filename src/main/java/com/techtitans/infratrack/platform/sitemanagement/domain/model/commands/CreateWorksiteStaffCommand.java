package com.techtitans.infratrack.platform.sitemanagement.domain.model.commands;

import com.techtitans.infratrack.platform.sitemanagement.domain.model.valueobjects.StaffStatus;

public record CreateWorksiteStaffCommand(
        String fullName,
        String email,
        String phone,
        String licenseNumber,
        StaffStatus status
) {
}
