package com.techtitans.infratrack.platform.fleet.domain.repositories;

import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.FleetOperator;

import java.util.List;
import java.util.Optional;

public interface FleetOperatorRepository {
    Optional<FleetOperator> findById(Long id);
    List<FleetOperator> findAll();
    boolean existsById(Long id);
    boolean existsByLicenseNumber(String licenseNumber);
    FleetOperator save(FleetOperator operator);
}
