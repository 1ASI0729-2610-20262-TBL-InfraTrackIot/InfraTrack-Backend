package com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.adapters;

import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.IotNode;
import com.techtitans.infratrack.platform.fleet.domain.repositories.IotNodeRepository;
import com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.assemblers.IotNodePersistenceAssembler;
import com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.repositories.IotNodePersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class IotNodeRepositoryImpl implements IotNodeRepository {

    private final IotNodePersistenceRepository iotNodePersistenceRepository;

    public IotNodeRepositoryImpl(IotNodePersistenceRepository iotNodePersistenceRepository) {
        this.iotNodePersistenceRepository = iotNodePersistenceRepository;
    }

    @Override
    public Optional<IotNode> findById(Long id) {
        return iotNodePersistenceRepository.findById(id).map(IotNodePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<IotNode> findAll() {
        return iotNodePersistenceRepository.findAll().stream().map(IotNodePersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override
    public boolean existsById(Long id) {
        return iotNodePersistenceRepository.existsById(id);
    }

    @Override
    public boolean existsByNodeIdentifier(String nodeIdentifier) {
        return iotNodePersistenceRepository.existsByNodeIdentifier(nodeIdentifier);
    }

    @Override
    public Optional<IotNode> findByNodeIdentifier(String nodeIdentifier) {
        return iotNodePersistenceRepository.findByNodeIdentifier(nodeIdentifier)
                .map(IotNodePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<IotNode> findByMachineryId(Long machineryId) {
        return iotNodePersistenceRepository.findFirstByMachineryId(machineryId)
                .map(IotNodePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public IotNode save(IotNode iotNode) {
        var saved = iotNodePersistenceRepository.save(IotNodePersistenceAssembler.toPersistenceFromDomain(iotNode));
        return IotNodePersistenceAssembler.toDomainFromPersistence(saved);
    }
}
