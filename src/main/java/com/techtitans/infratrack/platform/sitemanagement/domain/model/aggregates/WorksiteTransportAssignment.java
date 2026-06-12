package com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates;

import com.techtitans.infratrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

/**
 * v0.17.0 slice — persistence entity mapping only (assign command added in v0.19.0).
 */
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
}
