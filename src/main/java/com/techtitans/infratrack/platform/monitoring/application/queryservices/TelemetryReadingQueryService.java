package com.techtitans.infratrack.platform.monitoring.application.queryservices;

import com.techtitans.infratrack.platform.monitoring.domain.model.aggregates.TelemetryReading;
import com.techtitans.infratrack.platform.monitoring.domain.model.queries.GetAllTelemetryReadingsQuery;
import com.techtitans.infratrack.platform.monitoring.domain.model.queries.GetTelemetryReadingByIdQuery;

import java.util.List;
import java.util.Optional;

public interface TelemetryReadingQueryService {
    Optional<TelemetryReading> handle(GetTelemetryReadingByIdQuery query);
    List<TelemetryReading> handle(GetAllTelemetryReadingsQuery query);
}
