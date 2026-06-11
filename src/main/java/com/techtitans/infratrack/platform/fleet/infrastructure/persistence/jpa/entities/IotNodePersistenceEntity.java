package com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.entities;

import com.techtitans.infratrack.platform.fleet.domain.model.valueobjects.ConnectionStatus;
import com.techtitans.infratrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "iot_nodes")
@Getter
@Setter
@NoArgsConstructor
public class IotNodePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "machinery_id", nullable = false)
    private Long machineryId;

    @Column(name = "node_identifier", nullable = false, unique = true)
    private String nodeIdentifier;

    @Column(name = "firmware_version", nullable = false)
    private String firmwareVersion;

    @Column(name = "battery_voltage", nullable = false)
    private Double batteryVoltage;

    @Enumerated(EnumType.STRING)
    @Column(name = "connection_status", nullable = false)
    private ConnectionStatus connectionStatus;

    @Column(name = "last_seen", nullable = false)
    private Instant lastSeen;
}
