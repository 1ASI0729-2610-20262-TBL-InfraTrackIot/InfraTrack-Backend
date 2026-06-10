package com.techtitans.infratrack.platform.fleet.domain.repositories;

import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.IotNode;

import java.util.List;
import java.util.Optional;

public interface IotNodeRepository {
    Optional<IotNode> findById(Long id);
    List<IotNode> findAll();
    boolean existsById(Long id);
    boolean existsByNodeIdentifier(String nodeIdentifier);
    Optional<IotNode> findByNodeIdentifier(String nodeIdentifier);
    Optional<IotNode> findByMachineryId(Long machineryId);
    IotNode save(IotNode iotNode);
}
