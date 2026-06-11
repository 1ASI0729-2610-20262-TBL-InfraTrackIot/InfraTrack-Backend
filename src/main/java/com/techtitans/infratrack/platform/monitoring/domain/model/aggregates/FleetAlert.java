package com.techtitans.infratrack.platform.monitoring.domain.model.aggregates;

import com.techtitans.infratrack.platform.monitoring.domain.model.commands.CreateFleetAlertCommand;
import com.techtitans.infratrack.platform.monitoring.domain.model.valueobjects.AlertSeverity;
import com.techtitans.infratrack.platform.monitoring.domain.model.valueobjects.AlertType;
import com.techtitans.infratrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
public class FleetAlert extends AbstractDomainAggregateRoot<FleetAlert> {

    @Setter
    private Long id;
    @Setter
    private Long machineryId;
    @Setter
    private AlertType type;
    @Setter
    private AlertSeverity severity;
    @Setter
    private String description;
    @Setter
    private boolean acknowledged;
    @Setter
    private Instant timestamp;

    public FleetAlert() {
        this.acknowledged = false;
        this.timestamp = Instant.now();
    }

    public FleetAlert(CreateFleetAlertCommand command) {
        this.machineryId = command.machineryId();
        this.type = command.type();
        this.severity = command.severity();
        this.description = command.description();
        this.acknowledged = command.isAcknowledged();
        this.timestamp = command.timestamp() != null ? command.timestamp() : Instant.now();
    }

    public FleetAlert acknowledge() {
        this.acknowledged = true;
        return this;
    }
}
