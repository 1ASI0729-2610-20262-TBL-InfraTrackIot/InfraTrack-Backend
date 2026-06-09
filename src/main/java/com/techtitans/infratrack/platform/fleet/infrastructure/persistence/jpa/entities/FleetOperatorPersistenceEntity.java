package com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.entities;

import com.techtitans.infratrack.platform.fleet.domain.model.valueobjects.OperatorStatus;
import com.techtitans.infratrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fleet_operators")
@Getter
@Setter
@NoArgsConstructor
public class FleetOperatorPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "license_number", nullable = false, unique = true)
    private String licenseNumber;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperatorStatus status;
}
