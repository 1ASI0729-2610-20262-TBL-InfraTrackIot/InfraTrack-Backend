package com.techtitans.infratrack.platform.fleet.domain.model.aggregates;

import com.techtitans.infratrack.platform.fleet.domain.model.commands.CreateIotNodeCommand;
import com.techtitans.infratrack.platform.fleet.domain.model.valueobjects.ConnectionStatus;
import com.techtitans.infratrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
public class IotNode extends AbstractDomainAggregateRoot<IotNode> {

    @Setter
    private Long id;
    @Setter
    private Long machineryId;
    @Setter
    private String nodeIdentifier;
    @Setter
    private String firmwareVersion;
    @Setter
    private Double batteryVoltage;
    @Setter
    private ConnectionStatus connectionStatus;
    @Setter
    private Instant lastSeen;

    public IotNode() {
        this.connectionStatus = ConnectionStatus.ONLINE;
        this.lastSeen = Instant.now();
    }

    public IotNode(CreateIotNodeCommand command) {
        this.machineryId = command.machineryId();
        this.nodeIdentifier = command.nodeIdentifier();
        this.firmwareVersion = command.firmwareVersion();
        this.batteryVoltage = command.batteryVoltage();
        this.connectionStatus = command.connectionStatus();
        this.lastSeen = command.lastSeen() != null ? command.lastSeen() : Instant.now();
    }

    public IotNode linkToMachinery(Long machineryId) {
        this.machineryId = machineryId;
        return this;
    }
}
