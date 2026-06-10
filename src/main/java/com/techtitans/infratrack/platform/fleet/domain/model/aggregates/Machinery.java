package com.techtitans.infratrack.platform.fleet.domain.model.aggregates;

import com.techtitans.infratrack.platform.fleet.domain.model.commands.CreateMachineryCommand;
import com.techtitans.infratrack.platform.fleet.domain.model.valueobjects.FuelType;
import com.techtitans.infratrack.platform.fleet.domain.model.valueobjects.MachineryStatus;
import com.techtitans.infratrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
public class Machinery extends AbstractDomainAggregateRoot<Machinery> {

    @Setter
    private Long id;
    @Setter
    private Long operatorId;
    @Setter
    private String plateNumber;
    @Setter
    private String model;
    @Setter
    private String brand;
    @Setter
    private FuelType fuelType;
    @Setter
    private Integer tankCapacityLiters;
    @Setter
    private MachineryStatus currentStatus;
    @Setter
    private String imageUrl;
    @Setter
    private Date createdAt;

    public Machinery() {
        this.fuelType = FuelType.DIESEL;
        this.currentStatus = MachineryStatus.ACTIVE;
        this.imageUrl = "";
    }

    public Machinery(CreateMachineryCommand command) {
        this.operatorId = command.operatorId();
        this.plateNumber = command.plateNumber();
        this.model = command.model();
        this.brand = command.brand();
        this.fuelType = command.fuelType();
        this.tankCapacityLiters = command.tankCapacityLiters();
        this.currentStatus = command.currentStatus();
        this.imageUrl = command.imageUrl() != null ? command.imageUrl() : "";
    }

    public Machinery assignOperator(Long operatorId) {
        this.operatorId = operatorId;
        return this;
    }

    public Machinery updateStatus(MachineryStatus status) {
        this.currentStatus = status;
        return this;
    }
}
