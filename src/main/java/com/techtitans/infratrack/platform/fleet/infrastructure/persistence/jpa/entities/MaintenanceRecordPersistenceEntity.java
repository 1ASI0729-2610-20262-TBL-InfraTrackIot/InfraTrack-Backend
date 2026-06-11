package com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.entities;

import com.techtitans.infratrack.platform.fleet.domain.model.valueobjects.MaintenanceServiceType;
import com.techtitans.infratrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "maintenance_records")
@Getter
@Setter
@NoArgsConstructor
public class MaintenanceRecordPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "machinery_id", nullable = false)
    private Long machineryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type", nullable = false)
    private MaintenanceServiceType serviceType;

    @Column(nullable = false)
    private String description;

    @Column(name = "cost_pen", nullable = false)
    private Double costPen;

    @Column(name = "engine_hours_at_service", nullable = false)
    private Double engineHoursAtService;

    @Column(name = "service_date", nullable = false)
    private LocalDate serviceDate;

    @Column(name = "next_service_date", nullable = false)
    private LocalDate nextServiceDate;
}
