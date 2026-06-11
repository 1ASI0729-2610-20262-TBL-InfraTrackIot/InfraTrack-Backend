package com.techtitans.infratrack.platform.fleet.application.internal.commandservices;

import com.techtitans.infratrack.platform.fleet.application.commandservices.MaintenanceRecordCommandService;
import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.MaintenanceRecord;
import com.techtitans.infratrack.platform.fleet.domain.model.commands.CreateMaintenanceRecordCommand;
import com.techtitans.infratrack.platform.fleet.domain.repositories.MachineryRepository;
import com.techtitans.infratrack.platform.fleet.domain.repositories.MaintenanceRecordRepository;
import com.techtitans.infratrack.platform.shared.application.result.ApplicationError;
import com.techtitans.infratrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceRecordCommandServiceImpl implements MaintenanceRecordCommandService {

    private final MaintenanceRecordRepository maintenanceRecordRepository;
    private final MachineryRepository machineryRepository;

    public MaintenanceRecordCommandServiceImpl(
            MaintenanceRecordRepository maintenanceRecordRepository,
            MachineryRepository machineryRepository) {
        this.maintenanceRecordRepository = maintenanceRecordRepository;
        this.machineryRepository = machineryRepository;
    }

    @Override
    public Result<MaintenanceRecord, ApplicationError> handle(CreateMaintenanceRecordCommand command) {
        if (!machineryRepository.existsById(command.machineryId())) {
            return Result.failure(ApplicationError.notFound("Machinery", command.machineryId().toString()));
        }
        try {
            return Result.success(maintenanceRecordRepository.save(new MaintenanceRecord(command)));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-maintenance-record", e.getMessage()));
        }
    }
}
