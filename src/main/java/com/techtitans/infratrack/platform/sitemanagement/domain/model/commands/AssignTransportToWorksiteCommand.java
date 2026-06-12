package com.techtitans.infratrack.platform.sitemanagement.domain.model.commands;

public record AssignTransportToWorksiteCommand(
        Long worksiteId,
        Long transportId,
        String gpsLabel
) {
}
