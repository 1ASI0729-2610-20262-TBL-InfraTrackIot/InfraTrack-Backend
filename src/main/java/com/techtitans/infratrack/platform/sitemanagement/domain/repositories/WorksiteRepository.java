package com.techtitans.infratrack.platform.sitemanagement.domain.repositories;

import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.Worksite;

import java.util.List;
import java.util.Optional;

public interface WorksiteRepository {
    Optional<Worksite> findById(Long id);
    List<Worksite> findAll();
    boolean existsById(Long id);
    Worksite save(Worksite worksite);
}
