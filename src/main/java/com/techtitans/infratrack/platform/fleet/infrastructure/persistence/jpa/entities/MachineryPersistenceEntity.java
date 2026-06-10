package com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.entities;

import com.techtitans.infratrack.platform.fleet.domain.model.valueobjects.FuelType;
import com.techtitans.infratrack.platform.fleet.domain.model.valueobjects.MachineryStatus;
import com.techtitans.infratrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "machinery")
@Getter
@Setter
@NoArgsConstructor
public class MachineryPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "operator_id", nullable = false)
    private Long operatorId;

    @Column(name = "plate_number", nullable = false, unique = true)
    private String plateNumber;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String brand;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type", nullable = false)
    private FuelType fuelType;

    @Column(name = "tank_capacity_liters", nullable = false)
    private Integer tankCapacityLiters;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_status", nullable = false)
    private MachineryStatus currentStatus;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;
}
