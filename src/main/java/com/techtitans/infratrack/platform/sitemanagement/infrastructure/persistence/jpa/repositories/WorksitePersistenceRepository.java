package com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.repositories;

import com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.entities.WorksitePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorksitePersistenceRepository extends JpaRepository<WorksitePersistenceEntity, Long> {
}
