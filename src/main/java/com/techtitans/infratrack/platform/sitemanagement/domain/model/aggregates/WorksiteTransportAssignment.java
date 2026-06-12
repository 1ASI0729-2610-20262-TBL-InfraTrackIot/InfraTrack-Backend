package com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates;

import com.techtitans.infratrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.commands.AssignTransportToWorksiteCommand;
import lombok.Getter;
import lombok.Setter;

@Getter
public class WorksiteTransportAssignment extends AbstractDomainAggregateRoot<WorksiteTransportAssignment> {

    @Setter
    private Long id;
    @Setter
    private Long worksiteId;
    @Setter
    private Long machineryId;
    @Setter
    private String gpsLabel;

    public WorksiteTransportAssignment() {
        this.gpsLabel = "";
    }

    public WorksiteTransportAssignment(AssignTransportToWorksiteCommand command) {
        this.worksiteId = command.worksiteId();
        this.machineryId = command.transportId();
        this.gpsLabel = command.gpsLabel() != null ? command.gpsLabel() : "";
    }
}
