package com.techtitans.infratrack.platform.sitemanagement.domain.model.commands;

import com.techtitans.infratrack.platform.sitemanagement.domain.model.valueobjects.WorksiteStatus;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.valueobjects.WorksiteType;

public record CreateWorksiteCommand(
        String name,
        String city,
        WorksiteType type,
        WorksiteStatus status,
        String address,
        String leadEngineer,
        Double latitude,
        Double longitude
) {
}
