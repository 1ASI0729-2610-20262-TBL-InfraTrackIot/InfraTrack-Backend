package com.techtitans.infratrack.platform.fleet.application.queryservices;

import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.IotNode;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetAllIotNodesQuery;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetIotNodeByIdQuery;

import java.util.List;
import java.util.Optional;

public interface IotNodeQueryService {
    Optional<IotNode> handle(GetIotNodeByIdQuery query);
    List<IotNode> handle(GetAllIotNodesQuery query);
}
