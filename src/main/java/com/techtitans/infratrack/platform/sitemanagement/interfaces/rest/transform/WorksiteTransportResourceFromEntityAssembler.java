package com.techtitans.infratrack.platform.sitemanagement.interfaces.rest.transform;

import com.techtitans.infratrack.platform.sitemanagement.application.internal.outboundservices.acl.SiteManagementExternalFleetService;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.WorksiteTransportAssignment;
import com.techtitans.infratrack.platform.sitemanagement.interfaces.rest.resources.WorksiteTransportResource;

public final class WorksiteTransportResourceFromEntityAssembler {

    private WorksiteTransportResourceFromEntityAssembler() {
    }

    public static WorksiteTransportResource toResourceFromEntity(
            WorksiteTransportAssignment assignment,
            SiteManagementExternalFleetService externalFleetService) {
        var machinery = externalFleetService.fetchMachineryView(assignment.getMachineryId()).orElse(null);
        var nodeId = externalFleetService.fetchNodeIdentifierByMachineryId(assignment.getMachineryId()).orElse("");
        if (machinery == null) {
            return new WorksiteTransportResource(
                    assignment.getId(),
                    assignment.getWorksiteId(),
                    "",
                    "",
                    "",
                    nodeId,
                    assignment.getGpsLabel(),
                    0,
                    "inactive"
            );
        }
        return new WorksiteTransportResource(
                assignment.getId(),
                assignment.getWorksiteId(),
                machinery.plateNumber(),
                machinery.model(),
                machinery.brand(),
                nodeId,
                assignment.getGpsLabel(),
                0,
                machinery.currentStatus()
        );
    }
}
