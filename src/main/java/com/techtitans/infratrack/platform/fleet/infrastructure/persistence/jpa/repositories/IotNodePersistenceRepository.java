package com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.repositories;

import com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.entities.IotNodePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IotNodePersistenceRepository extends JpaRepository<IotNodePersistenceEntity, Long> {
    boolean existsByNodeIdentifier(String nodeIdentifier);
    Optional<IotNodePersistenceEntity> findByNodeIdentifier(String nodeIdentifier);
    Optional<IotNodePersistenceEntity> findFirstByMachineryId(Long machineryId);
}
