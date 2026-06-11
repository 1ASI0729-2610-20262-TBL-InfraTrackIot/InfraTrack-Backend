package com.techtitans.infratrack.platform.fleet.domain.model.aggregates;

import com.techtitans.infratrack.platform.fleet.domain.model.commands.CreateMaintenanceRecordCommand;
import com.techtitans.infratrack.platform.fleet.domain.model.valueobjects.MaintenanceServiceType;
import com.techtitans.infratrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
public class MaintenanceRecord extends AbstractDomainAggregateRoot<MaintenanceRecord> {

    @Setter
    private Long id;
    @Setter
    private Long machineryId;
    @Setter
    private MaintenanceServiceType serviceType;
    @Setter
    private String description;
    @Setter
    private Double costPen;
    @Setter
    private Double engineHoursAtService;
    @Setter
    private LocalDate serviceDate;
    @Setter
    private LocalDate nextServiceDate;

    public MaintenanceRecord() {
    }

    public MaintenanceRecord(CreateMaintenanceRecordCommand command) {
        this.machineryId = command.machineryId();
        this.serviceType = command.serviceType();
        this.description = command.description();
        this.costPen = command.costPen();
        this.engineHoursAtService = command.engineHoursAtService();
        this.serviceDate = command.serviceDate();
        this.nextServiceDate = command.nextServiceDate();
    }
}
