package com.techtitans.infratrack.platform.monitoring.infrastructure.persistence.jpa.entities;

import com.techtitans.infratrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "telemetry_data")
@Getter
@Setter
@NoArgsConstructor
public class TelemetryReadingPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "node_id", nullable = false)
    private Long nodeId;

    @Column(name = "fuel_level", nullable = false)
    private Double fuelLevel;

    @Column(name = "fuel_level_pct", nullable = false)
    private Integer fuelLevelPct;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(name = "engine_hours", nullable = false)
    private Double engineHours;

    @Column(name = "speed_kmh", nullable = false)
    private Double speedKmh;

    @Column(name = "engine_on", nullable = false)
    private Boolean engineOn;

    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt;
}
