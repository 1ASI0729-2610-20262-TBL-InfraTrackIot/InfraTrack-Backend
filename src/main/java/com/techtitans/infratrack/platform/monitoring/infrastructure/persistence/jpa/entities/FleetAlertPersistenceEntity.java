package com.techtitans.infratrack.platform.monitoring.infrastructure.persistence.jpa.entities;

import com.techtitans.infratrack.platform.monitoring.domain.model.valueobjects.AlertSeverity;
import com.techtitans.infratrack.platform.monitoring.domain.model.valueobjects.AlertType;
import com.techtitans.infratrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "alerts")
@Getter
@Setter
@NoArgsConstructor
public class FleetAlertPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "machinery_id", nullable = false)
    private Long machineryId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertSeverity severity;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(name = "is_acknowledged", nullable = false)
    private boolean acknowledged;

    @Column(nullable = false)
    private Instant timestamp;
}
