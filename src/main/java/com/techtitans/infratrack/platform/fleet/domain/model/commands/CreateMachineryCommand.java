package com.techtitans.infratrack.platform.fleet.domain.model.commands;

import com.techtitans.infratrack.platform.fleet.domain.model.valueobjects.FuelType;
import com.techtitans.infratrack.platform.fleet.domain.model.valueobjects.MachineryStatus;

public record CreateMachineryCommand(
        Long operatorId,
        String plateNumber,
        String model,
        String brand,
        FuelType fuelType,
        Integer tankCapacityLiters,
        MachineryStatus currentStatus,
        String imageUrl
) {
}
