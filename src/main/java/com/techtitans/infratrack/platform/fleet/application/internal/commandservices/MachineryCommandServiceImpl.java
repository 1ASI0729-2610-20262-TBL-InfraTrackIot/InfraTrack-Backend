package com.techtitans.infratrack.platform.fleet.application.internal.commandservices;

import com.techtitans.infratrack.platform.fleet.application.commandservices.MachineryCommandService;
import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.Machinery;
import com.techtitans.infratrack.platform.fleet.domain.model.commands.CreateMachineryCommand;
import com.techtitans.infratrack.platform.fleet.domain.model.commands.UpdateMachineryCommand;
import com.techtitans.infratrack.platform.fleet.domain.repositories.FleetOperatorRepository;
import com.techtitans.infratrack.platform.fleet.domain.repositories.MachineryRepository;
import com.techtitans.infratrack.platform.shared.application.result.ApplicationError;
import com.techtitans.infratrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class MachineryCommandServiceImpl implements MachineryCommandService {

    private final MachineryRepository machineryRepository;
    private final FleetOperatorRepository fleetOperatorRepository;

    public MachineryCommandServiceImpl(
            MachineryRepository machineryRepository,
            FleetOperatorRepository fleetOperatorRepository) {
        this.machineryRepository = machineryRepository;
        this.fleetOperatorRepository = fleetOperatorRepository;
    }

    @Override
    public Result<Machinery, ApplicationError> handle(CreateMachineryCommand command) {
        if (machineryRepository.existsByPlateNumber(command.plateNumber())) {
            return Result.failure(ApplicationError.conflict("Machinery", "Plate '%s' already exists".formatted(command.plateNumber())));
        }
        if (!fleetOperatorRepository.existsById(command.operatorId())) {
            return Result.failure(ApplicationError.notFound("Operator", command.operatorId().toString()));
        }
        try {
            var saved = machineryRepository.save(new Machinery(command));
            return Result.success(saved);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-machinery", e.getMessage()));
        }
    }

    @Override
    public Result<Machinery, ApplicationError> handle(UpdateMachineryCommand command) {
        var machinery = machineryRepository.findById(command.machineryId());
        if (machinery.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Machinery", command.machineryId().toString()));
        }
        if (command.operatorId() != null && !fleetOperatorRepository.existsById(command.operatorId())) {
            return Result.failure(ApplicationError.notFound("Operator", command.operatorId().toString()));
        }
        try {
            var current = machinery.get();
            if (command.operatorId() != null) {
                current.assignOperator(command.operatorId());
            }
            if (command.currentStatus() != null) {
                current.updateStatus(command.currentStatus());
            }
            return Result.success(machineryRepository.save(current));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("update-machinery", e.getMessage()));
        }
    }
}
