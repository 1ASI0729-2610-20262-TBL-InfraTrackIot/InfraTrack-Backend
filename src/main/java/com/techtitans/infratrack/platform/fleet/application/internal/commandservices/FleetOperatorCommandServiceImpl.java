package com.techtitans.infratrack.platform.fleet.application.internal.commandservices;

import com.techtitans.infratrack.platform.fleet.application.commandservices.FleetOperatorCommandService;
import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.FleetOperator;
import com.techtitans.infratrack.platform.fleet.domain.model.commands.CreateFleetOperatorCommand;
import com.techtitans.infratrack.platform.fleet.domain.repositories.FleetOperatorRepository;
import com.techtitans.infratrack.platform.shared.application.result.ApplicationError;
import com.techtitans.infratrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class FleetOperatorCommandServiceImpl implements FleetOperatorCommandService {

    private final FleetOperatorRepository fleetOperatorRepository;

    public FleetOperatorCommandServiceImpl(FleetOperatorRepository fleetOperatorRepository) {
        this.fleetOperatorRepository = fleetOperatorRepository;
    }

    @Override
    public Result<FleetOperator, ApplicationError> handle(CreateFleetOperatorCommand command) {
        if (fleetOperatorRepository.existsByLicenseNumber(command.licenseNumber())) {
            return Result.failure(ApplicationError.conflict("Operator", "License '%s' already exists".formatted(command.licenseNumber())));
        }
        try {
            return Result.success(fleetOperatorRepository.save(new FleetOperator(command)));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-operator", e.getMessage()));
        }
    }
}
