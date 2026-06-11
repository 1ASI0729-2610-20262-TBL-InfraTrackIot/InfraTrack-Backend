package com.techtitans.infratrack.platform.monitoring.application.internal.queryservices;

import com.techtitans.infratrack.platform.monitoring.application.queryservices.TelemetryReadingQueryService;
import com.techtitans.infratrack.platform.monitoring.domain.model.aggregates.TelemetryReading;
import com.techtitans.infratrack.platform.monitoring.domain.model.queries.GetAllTelemetryReadingsQuery;
import com.techtitans.infratrack.platform.monitoring.domain.model.queries.GetTelemetryReadingByIdQuery;
import com.techtitans.infratrack.platform.monitoring.domain.repositories.TelemetryReadingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TelemetryReadingQueryServiceImpl implements TelemetryReadingQueryService {

    private final TelemetryReadingRepository telemetryReadingRepository;

    public TelemetryReadingQueryServiceImpl(TelemetryReadingRepository telemetryReadingRepository) {
        this.telemetryReadingRepository = telemetryReadingRepository;
    }

    @Override
    public Optional<TelemetryReading> handle(GetTelemetryReadingByIdQuery query) {
        return telemetryReadingRepository.findById(query.telemetryReadingId());
    }

    @Override
    public List<TelemetryReading> handle(GetAllTelemetryReadingsQuery query) {
        return telemetryReadingRepository.findAll();
    }
}
