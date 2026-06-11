package com.techtitans.infratrack.platform.monitoring.application.internal.commandservices;

import com.techtitans.infratrack.platform.monitoring.application.commandservices.TelemetryReadingCommandService;
import com.techtitans.infratrack.platform.monitoring.application.internal.outboundservices.acl.ExternalFleetService;
import com.techtitans.infratrack.platform.monitoring.domain.model.aggregates.TelemetryReading;
import com.techtitans.infratrack.platform.monitoring.domain.model.commands.CreateTelemetryReadingCommand;
import com.techtitans.infratrack.platform.monitoring.domain.repositories.TelemetryReadingRepository;
import com.techtitans.infratrack.platform.shared.application.result.ApplicationError;
import com.techtitans.infratrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class TelemetryReadingCommandServiceImpl implements TelemetryReadingCommandService {

    private final TelemetryReadingRepository telemetryReadingRepository;
    private final ExternalFleetService externalFleetService;

    public TelemetryReadingCommandServiceImpl(
            TelemetryReadingRepository telemetryReadingRepository,
            ExternalFleetService externalFleetService) {
        this.telemetryReadingRepository = telemetryReadingRepository;
        this.externalFleetService = externalFleetService;
    }

    @Override
    public Result<TelemetryReading, ApplicationError> handle(CreateTelemetryReadingCommand command) {
        if (!externalFleetService.iotNodeExists(command.nodeId())) {
            return Result.failure(ApplicationError.notFound("IotNode", command.nodeId().toString()));
        }
        try {
            return Result.success(telemetryReadingRepository.save(new TelemetryReading(command)));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-telemetry-reading", e.getMessage()));
        }
    }
}
