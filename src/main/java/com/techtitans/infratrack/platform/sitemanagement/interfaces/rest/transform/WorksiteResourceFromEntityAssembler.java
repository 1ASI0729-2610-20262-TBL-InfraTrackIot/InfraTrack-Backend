package com.techtitans.infratrack.platform.sitemanagement.interfaces.rest.transform;

import com.techtitans.infratrack.platform.sitemanagement.application.queryservices.WorksiteQueryService;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.Worksite;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.commands.CreateWorksiteCommand;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.valueobjects.WorksiteStatus;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.valueobjects.WorksiteType;
import com.techtitans.infratrack.platform.sitemanagement.interfaces.rest.resources.CreateWorksiteResource;
import com.techtitans.infratrack.platform.sitemanagement.interfaces.rest.resources.WorksiteResource;

public final class WorksiteResourceFromEntityAssembler {

    private WorksiteResourceFromEntityAssembler() {
    }

    public static WorksiteResource toResourceFromEntity(Worksite worksite, WorksiteQueryService worksiteQueryService) {
        return new WorksiteResource(
                worksite.getId(),
                worksite.getName(),
                worksite.getCity(),
                worksite.getType().toApiValue(),
                worksite.getStatus().toApiValue(),
                worksite.getAddress(),
                worksite.getLeadEngineer(),
                worksiteQueryService.countTransports(worksite.getId()),
                worksiteQueryService.countStaff(worksite.getId()),
                worksite.getLatitude(),
                worksite.getLongitude()
        );
    }

    public static CreateWorksiteCommand toCreateCommandFromResource(CreateWorksiteResource resource) {
        return new CreateWorksiteCommand(
                resource.name(),
                resource.city(),
                WorksiteType.fromApiValue(resource.type()),
                WorksiteStatus.fromApiValue(resource.status()),
                resource.address(),
                resource.leadEngineer(),
                resource.latitude(),
                resource.longitude()
        );
    }
}
