package com.techtitans.infratrack.platform.fleet.domain.repositories;

import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.Machinery;

import java.util.List;
import java.util.Optional;

public interface MachineryRepository {
    Optional<Machinery> findById(Long id);
    List<Machinery> findAll();
    boolean existsById(Long id);
    boolean existsByPlateNumber(String plateNumber);
    Optional<Machinery> findByPlateNumber(String plateNumber);
    Machinery save(Machinery machinery);
}
