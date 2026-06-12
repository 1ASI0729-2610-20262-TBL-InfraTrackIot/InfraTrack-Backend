package com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.entities;

import com.techtitans.infratrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "worksite_transport_assignments")
@Getter
@Setter
@NoArgsConstructor
public class WorksiteTransportAssignmentPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "worksite_id", nullable = false)
    private Long worksiteId;

    @Column(name = "machinery_id", nullable = false, unique = true)
    private Long machineryId;

    @Column(name = "gps_label", nullable = false)
    private String gpsLabel;
}
