package com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.repositories;

import com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.entities.WorksiteStaffPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorksiteStaffPersistenceRepository extends JpaRepository<WorksiteStaffPersistenceEntity, Long> {
    boolean existsByEmailIgnoreCase(String email);
}
