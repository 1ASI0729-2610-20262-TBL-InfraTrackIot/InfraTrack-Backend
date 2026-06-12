package com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.entities;

import com.techtitans.infratrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.valueobjects.StaffStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "worksite_staff")
@Getter
@Setter
@NoArgsConstructor
public class WorksiteStaffPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(name = "license_number", nullable = false)
    private String licenseNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StaffStatus status;

    @Column(name = "alerts_last_30_days", nullable = false)
    private Integer alertsLast30Days;

    @Column(name = "driving_hours_week", nullable = false)
    private Integer drivingHoursWeek;

    @Column(name = "current_vehicle")
    private String currentVehicle;
}
