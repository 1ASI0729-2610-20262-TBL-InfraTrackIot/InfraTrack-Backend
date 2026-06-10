package com.techtitans.infratrack.platform.fleet.interfaces.rest.transform;

import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.Machinery;
import com.techtitans.infratrack.platform.fleet.domain.model.commands.CreateMachineryCommand;
import com.techtitans.infratrack.platform.fleet.domain.model.commands.UpdateMachineryCommand;
import com.techtitans.infratrack.platform.fleet.domain.model.valueobjects.FuelType;
import com.techtitans.infratrack.platform.fleet.domain.model.valueobjects.MachineryStatus;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.CreateMachineryResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.MachineryResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.UpdateMachineryResource;

import java.time.Instant;

public final class MachineryResourceFromEntityAssembler {

    private MachineryResourceFromEntityAssembler() {
    }

    public static MachineryResource toResourceFromEntity(Machinery machinery) {
        var createdAt = machinery.getCreatedAt() != null
                ? machinery.getCreatedAt().toInstant().toString()
                : Instant.now().toString();
        return new MachineryResource(
                machinery.getId(),
                machinery.getOperatorId(),
                machinery.getPlateNumber(),
                machinery.getModel(),
                machinery.getBrand(),
                machinery.getFuelType().toApiValue(),
                machinery.getTankCapacityLiters(),
                machinery.getCurrentStatus().toApiValue(),
                machinery.getImageUrl(),
                createdAt
        );
    }

    public static CreateMachineryCommand toCreateCommandFromResource(CreateMachineryResource resource) {
        return new CreateMachineryCommand(
                resource.operatorId(),
                resource.plateNumber(),
                resource.model(),
                resource.brand(),
                FuelType.fromApiValue(resource.fuelType()),
                resource.tankCapacityLiters(),
                MachineryStatus.fromApiValue(resource.currentStatus()),
                resource.imageUrl()
        );
    }

    public static UpdateMachineryCommand toUpdateCommandFromResource(Long machineryId, UpdateMachineryResource resource) {
        return new UpdateMachineryCommand(
                machineryId,
                resource.operatorId(),
                resource.currentStatus() != null ? MachineryStatus.fromApiValue(resource.currentStatus()) : null
        );
    }
}
