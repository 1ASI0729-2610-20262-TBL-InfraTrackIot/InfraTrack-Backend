package com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.entities;

import com.techtitans.infratrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.valueobjects.WorksiteStatus;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.valueobjects.WorksiteType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "worksites")
@Getter
@Setter
@NoArgsConstructor
public class WorksitePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorksiteType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorksiteStatus status;

    @Column(nullable = false)
    private String address;

    @Column(name = "lead_engineer", nullable = false)
    private String leadEngineer;

    private Double latitude;

    private Double longitude;
}
