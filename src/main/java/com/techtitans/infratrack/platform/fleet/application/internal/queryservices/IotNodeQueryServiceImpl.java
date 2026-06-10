package com.techtitans.infratrack.platform.fleet.application.internal.queryservices;

import com.techtitans.infratrack.platform.fleet.application.queryservices.IotNodeQueryService;
import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.IotNode;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetAllIotNodesQuery;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetIotNodeByIdQuery;
import com.techtitans.infratrack.platform.fleet.domain.repositories.IotNodeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IotNodeQueryServiceImpl implements IotNodeQueryService {

    private final IotNodeRepository iotNodeRepository;

    public IotNodeQueryServiceImpl(IotNodeRepository iotNodeRepository) {
        this.iotNodeRepository = iotNodeRepository;
    }

    @Override
    public Optional<IotNode> handle(GetIotNodeByIdQuery query) {
        return iotNodeRepository.findById(query.iotNodeId());
    }

    @Override
    public List<IotNode> handle(GetAllIotNodesQuery query) {
        return iotNodeRepository.findAll();
    }
}
