package com.techtitans.infratrack.platform.fleet.domain.model.commands;

import com.techtitans.infratrack.platform.fleet.domain.model.valueobjects.MachineryStatus;

public record UpdateMachineryCommand(
        Long machineryId,
        Long operatorId,
        MachineryStatus currentStatus
) {
}
