package com.techtitans.infratrack.platform.monitoring.application.internal.queryservices;

import com.techtitans.infratrack.platform.monitoring.application.queryservices.FleetAlertQueryService;
import com.techtitans.infratrack.platform.monitoring.domain.model.aggregates.FleetAlert;
import com.techtitans.infratrack.platform.monitoring.domain.model.queries.GetAllFleetAlertsQuery;
import com.techtitans.infratrack.platform.monitoring.domain.model.queries.GetFleetAlertByIdQuery;
import com.techtitans.infratrack.platform.monitoring.domain.repositories.FleetAlertRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FleetAlertQueryServiceImpl implements FleetAlertQueryService {

    private final FleetAlertRepository fleetAlertRepository;

    public FleetAlertQueryServiceImpl(FleetAlertRepository fleetAlertRepository) {
        this.fleetAlertRepository = fleetAlertRepository;
    }

    @Override
    public Optional<FleetAlert> handle(GetFleetAlertByIdQuery query) {
        return fleetAlertRepository.findById(query.alertId());
    }

    @Override
    public List<FleetAlert> handle(GetAllFleetAlertsQuery query) {
        return fleetAlertRepository.findAll();
    }
}
