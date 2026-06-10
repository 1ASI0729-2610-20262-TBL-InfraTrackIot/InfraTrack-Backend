package com.techtitans.infratrack.platform.fleet.application.internal.queryservices;

import com.techtitans.infratrack.platform.fleet.application.queryservices.FleetOperatorQueryService;
import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.FleetOperator;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetAllFleetOperatorsQuery;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetFleetOperatorByIdQuery;
import com.techtitans.infratrack.platform.fleet.domain.repositories.FleetOperatorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FleetOperatorQueryServiceImpl implements FleetOperatorQueryService {

    private final FleetOperatorRepository fleetOperatorRepository;

    public FleetOperatorQueryServiceImpl(FleetOperatorRepository fleetOperatorRepository) {
        this.fleetOperatorRepository = fleetOperatorRepository;
    }

    @Override
    public Optional<FleetOperator> handle(GetFleetOperatorByIdQuery query) {
        return fleetOperatorRepository.findById(query.operatorId());
    }

    @Override
    public List<FleetOperator> handle(GetAllFleetOperatorsQuery query) {
        return fleetOperatorRepository.findAll();
    }
}
