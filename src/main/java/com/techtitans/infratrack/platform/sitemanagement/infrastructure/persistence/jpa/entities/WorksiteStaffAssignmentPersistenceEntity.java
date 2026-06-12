package com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.entities;

import com.techtitans.infratrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "worksite_staff_assignments")
@Getter
@Setter
@NoArgsConstructor
public class WorksiteStaffAssignmentPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "staff_id", nullable = false)
    private Long staffId;

    @Column(name = "worksite_id", nullable = false)
    private Long worksiteId;
}
