package com.techtitans.infratrack.platform.fleet.interfaces.rest.transform;

import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.IotNode;
import com.techtitans.infratrack.platform.fleet.domain.model.commands.CreateIotNodeCommand;
import com.techtitans.infratrack.platform.fleet.domain.model.valueobjects.ConnectionStatus;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.CreateIotNodeResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.IotNodeResource;

import java.time.Instant;

public final class IotNodeResourceFromEntityAssembler {

    private IotNodeResourceFromEntityAssembler() {
    }

    public static IotNodeResource toResourceFromEntity(IotNode node) {
        return new IotNodeResource(
                node.getId(),
                node.getMachineryId(),
                node.getNodeIdentifier(),
                node.getFirmwareVersion(),
                node.getBatteryVoltage(),
                node.getConnectionStatus().toApiValue(),
                node.getLastSeen() != null ? node.getLastSeen().toString() : Instant.now().toString()
        );
    }

    public static CreateIotNodeCommand toCreateCommandFromResource(CreateIotNodeResource resource) {
        Instant lastSeen = resource.lastSeen() != null && !resource.lastSeen().isBlank()
                ? Instant.parse(resource.lastSeen())
                : Instant.now();
        return new CreateIotNodeCommand(
                resource.machineryId(),
                resource.nodeIdentifier(),
                resource.firmwareVersion(),
                resource.batteryVoltage(),
                ConnectionStatus.fromApiValue(resource.connectionStatus()),
                lastSeen
        );
    }
}
