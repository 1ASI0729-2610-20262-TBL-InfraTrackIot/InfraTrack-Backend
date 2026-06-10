package com.techtitans.infratrack.platform.fleet.interfaces.rest.transform;

import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.FleetOperator;
import com.techtitans.infratrack.platform.fleet.domain.model.commands.CreateFleetOperatorCommand;
import com.techtitans.infratrack.platform.fleet.domain.model.valueobjects.OperatorStatus;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.CreateOperatorResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.OperatorResource;

public final class OperatorResourceFromEntityAssembler {

    private OperatorResourceFromEntityAssembler() {
    }

    public static OperatorResource toResourceFromEntity(FleetOperator operator) {
        return new OperatorResource(
                operator.getId(),
                operator.getUserId(),
                operator.getFullName(),
                operator.getLicenseNumber(),
                operator.getPhone(),
                operator.getStatus().toApiValue()
        );
    }

    public static CreateFleetOperatorCommand toCreateCommandFromResource(CreateOperatorResource resource) {
        return new CreateFleetOperatorCommand(
                resource.userId(),
                resource.fullName(),
                resource.licenseNumber(),
                resource.phone(),
                OperatorStatus.fromApiValue(resource.status())
        );
    }
}
