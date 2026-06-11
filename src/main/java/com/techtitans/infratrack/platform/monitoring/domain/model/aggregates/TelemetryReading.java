package com.techtitans.infratrack.platform.monitoring.domain.model.aggregates;

import com.techtitans.infratrack.platform.monitoring.domain.model.commands.CreateTelemetryReadingCommand;
import com.techtitans.infratrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
public class TelemetryReading extends AbstractDomainAggregateRoot<TelemetryReading> {

    @Setter
    private Long id;
    @Setter
    private Long nodeId;
    @Setter
    private Double fuelLevel;
    @Setter
    private Integer fuelLevelPct;
    @Setter
    private Double latitude;
    @Setter
    private Double longitude;
    @Setter
    private Double engineHours;
    @Setter
    private Double speedKmh;
    @Setter
    private Boolean engineOn;
    @Setter
    private Instant recordedAt;

    public TelemetryReading() {
        this.engineOn = true;
        this.recordedAt = Instant.now();
    }

    public TelemetryReading(CreateTelemetryReadingCommand command) {
        this.nodeId = command.nodeId();
        this.fuelLevel = command.fuelLevel();
        this.fuelLevelPct = command.fuelLevelPct();
        this.latitude = command.latitude();
        this.longitude = command.longitude();
        this.engineHours = command.engineHours();
        this.speedKmh = command.speedKmh();
        this.engineOn = command.engineOn();
        this.recordedAt = command.recordedAt() != null ? command.recordedAt() : Instant.now();
    }
}
