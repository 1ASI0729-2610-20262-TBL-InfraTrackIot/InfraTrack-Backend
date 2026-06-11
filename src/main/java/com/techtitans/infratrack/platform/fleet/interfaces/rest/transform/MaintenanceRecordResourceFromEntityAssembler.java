package com.techtitans.infratrack.platform.fleet.interfaces.rest.transform;

import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.MaintenanceRecord;
import com.techtitans.infratrack.platform.fleet.domain.model.commands.CreateMaintenanceRecordCommand;
import com.techtitans.infratrack.platform.fleet.domain.model.valueobjects.MaintenanceServiceType;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.CreateMaintenanceRecordResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.MaintenanceRecordResource;

import java.time.LocalDate;

public final class MaintenanceRecordResourceFromEntityAssembler {

    private MaintenanceRecordResourceFromEntityAssembler() {
    }

    public static MaintenanceRecordResource toResourceFromEntity(MaintenanceRecord record) {
        return new MaintenanceRecordResource(
                record.getId(),
                record.getMachineryId(),
                record.getServiceType().toApiValue(),
                record.getDescription(),
                record.getCostPen(),
                record.getEngineHoursAtService(),
                record.getServiceDate().toString(),
                record.getNextServiceDate().toString()
        );
    }

    public static CreateMaintenanceRecordCommand toCreateCommandFromResource(CreateMaintenanceRecordResource resource) {
        return new CreateMaintenanceRecordCommand(
                resource.machineryId(),
                MaintenanceServiceType.fromApiValue(resource.serviceType()),
                resource.description(),
                resource.costPen(),
                resource.engineHoursAtService(),
                LocalDate.parse(resource.serviceDate()),
                LocalDate.parse(resource.nextServiceDate())
        );
    }
}
