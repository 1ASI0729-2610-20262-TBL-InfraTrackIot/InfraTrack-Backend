package com.techtitans.infratrack.platform.fleet.application.queryservices;

import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.FleetOperator;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetAllFleetOperatorsQuery;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetFleetOperatorByIdQuery;

import java.util.List;
import java.util.Optional;

public interface FleetOperatorQueryService {
    Optional<FleetOperator> handle(GetFleetOperatorByIdQuery query);
    List<FleetOperator> handle(GetAllFleetOperatorsQuery query);
}
