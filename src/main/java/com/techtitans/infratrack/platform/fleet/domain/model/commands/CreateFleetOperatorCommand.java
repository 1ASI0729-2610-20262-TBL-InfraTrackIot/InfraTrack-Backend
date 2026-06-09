package com.techtitans.infratrack.platform.fleet.domain.model.commands;

import com.techtitans.infratrack.platform.fleet.domain.model.valueobjects.OperatorStatus;

public record CreateFleetOperatorCommand(
        Long userId,
        String fullName,
        String licenseNumber,
        String phone,
        OperatorStatus status
) {
}
