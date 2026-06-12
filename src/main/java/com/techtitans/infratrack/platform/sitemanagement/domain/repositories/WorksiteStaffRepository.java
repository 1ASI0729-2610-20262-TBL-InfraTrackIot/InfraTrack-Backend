package com.techtitans.infratrack.platform.sitemanagement.domain.repositories;

import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.WorksiteStaff;

import java.util.List;
import java.util.Optional;

public interface WorksiteStaffRepository {
    Optional<WorksiteStaff> findById(Long id);
    List<WorksiteStaff> findAll();
    boolean existsById(Long id);
    boolean existsByEmailIgnoreCase(String email);
    WorksiteStaff save(WorksiteStaff staff);
}
