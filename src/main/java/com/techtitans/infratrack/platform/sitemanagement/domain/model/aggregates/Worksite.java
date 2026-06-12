package com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates;

import com.techtitans.infratrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.commands.CreateWorksiteCommand;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.valueobjects.WorksiteStatus;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.valueobjects.WorksiteType;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Worksite extends AbstractDomainAggregateRoot<Worksite> {

    @Setter
    private Long id;
    @Setter
    private String name;
    @Setter
    private String city;
    @Setter
    private WorksiteType type;
    @Setter
    private WorksiteStatus status;
    @Setter
    private String address;
    @Setter
    private String leadEngineer;
    @Setter
    private Double latitude;
    @Setter
    private Double longitude;

    public Worksite() {
        this.type = WorksiteType.ROAD;
        this.status = WorksiteStatus.ACTIVE;
    }

    public Worksite(CreateWorksiteCommand command) {
        this.name = command.name();
        this.city = command.city();
        this.type = command.type();
        this.status = command.status();
        this.address = command.address();
        this.leadEngineer = command.leadEngineer();
        this.latitude = command.latitude();
        this.longitude = command.longitude();
    }
}
