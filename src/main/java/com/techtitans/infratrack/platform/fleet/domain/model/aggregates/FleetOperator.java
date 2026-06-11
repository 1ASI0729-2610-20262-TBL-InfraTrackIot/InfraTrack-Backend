package com.techtitans.infratrack.platform.fleet.domain.model.aggregates;

import com.techtitans.infratrack.platform.fleet.domain.model.commands.CreateFleetOperatorCommand;
import com.techtitans.infratrack.platform.fleet.domain.model.valueobjects.OperatorStatus;
import com.techtitans.infratrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

@Getter
public class FleetOperator extends AbstractDomainAggregateRoot<FleetOperator> {

    @Setter
    private Long id;
    @Setter
    private Long userId;
    @Setter
    private String fullName;
    @Setter
    private String licenseNumber;
    @Setter
    private String phone;
    @Setter
    private OperatorStatus status;

    public FleetOperator() {
        this.status = OperatorStatus.ACTIVE;
    }

    public FleetOperator(CreateFleetOperatorCommand command) {
        this.userId = command.userId();
        this.fullName = command.fullName();
        this.licenseNumber = command.licenseNumber();
        this.phone = command.phone();
        this.status = command.status();
    }
}
