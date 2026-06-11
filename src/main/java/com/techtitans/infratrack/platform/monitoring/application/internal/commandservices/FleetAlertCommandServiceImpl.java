package com.techtitans.infratrack.platform.monitoring.application.internal.commandservices;

import com.techtitans.infratrack.platform.monitoring.application.commandservices.FleetAlertCommandService;
import com.techtitans.infratrack.platform.monitoring.application.internal.outboundservices.acl.ExternalFleetService;
import com.techtitans.infratrack.platform.monitoring.domain.model.aggregates.FleetAlert;
import com.techtitans.infratrack.platform.monitoring.domain.model.commands.AcknowledgeFleetAlertCommand;
import com.techtitans.infratrack.platform.monitoring.domain.model.commands.CreateFleetAlertCommand;
import com.techtitans.infratrack.platform.monitoring.domain.repositories.FleetAlertRepository;
import com.techtitans.infratrack.platform.shared.application.result.ApplicationError;
import com.techtitans.infratrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class FleetAlertCommandServiceImpl implements FleetAlertCommandService {

    private final FleetAlertRepository fleetAlertRepository;
    private final ExternalFleetService externalFleetService;

    public FleetAlertCommandServiceImpl(
            FleetAlertRepository fleetAlertRepository,
            ExternalFleetService externalFleetService) {
        this.fleetAlertRepository = fleetAlertRepository;
        this.externalFleetService = externalFleetService;
    }

    @Override
    public Result<FleetAlert, ApplicationError> handle(CreateFleetAlertCommand command) {
        if (!externalFleetService.machineryExists(command.machineryId())) {
            return Result.failure(ApplicationError.notFound("Machinery", command.machineryId().toString()));
        }
        try {
            return Result.success(fleetAlertRepository.save(new FleetAlert(command)));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-fleet-alert", e.getMessage()));
        }
    }

    @Override
    public Result<FleetAlert, ApplicationError> handle(AcknowledgeFleetAlertCommand command) {
        var alert = fleetAlertRepository.findById(command.alertId());
        if (alert.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Alert", command.alertId().toString()));
        }
        try {
            return Result.success(fleetAlertRepository.save(alert.get().acknowledge()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("acknowledge-fleet-alert", e.getMessage()));
        }
    }
}
