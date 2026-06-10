package com.techtitans.infratrack.platform.monitoring.application.queryservices;

import com.techtitans.infratrack.platform.monitoring.domain.model.aggregates.FleetAlert;
import com.techtitans.infratrack.platform.monitoring.domain.model.queries.GetAllFleetAlertsQuery;
import com.techtitans.infratrack.platform.monitoring.domain.model.queries.GetFleetAlertByIdQuery;

import java.util.List;
import java.util.Optional;

public interface FleetAlertQueryService {
    Optional<FleetAlert> handle(GetFleetAlertByIdQuery query);
    List<FleetAlert> handle(GetAllFleetAlertsQuery query);
}
