package com.techtitans.infratrack.platform.sitemanagement.interfaces.rest.transform;

import com.techtitans.infratrack.platform.sitemanagement.application.queryservices.WorksiteStaffQueryService;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.WorksiteStaff;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.commands.CreateWorksiteStaffCommand;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.valueobjects.StaffStatus;
import com.techtitans.infratrack.platform.sitemanagement.interfaces.rest.resources.CreateWorksiteStaffResource;
import com.techtitans.infratrack.platform.sitemanagement.interfaces.rest.resources.WorksiteStaffResource;

public final class WorksiteStaffResourceFromEntityAssembler {

    private WorksiteStaffResourceFromEntityAssembler() {
    }

    public static WorksiteStaffResource toResourceFromEntity(
            WorksiteStaff staff,
            WorksiteStaffQueryService worksiteStaffQueryService) {
        return new WorksiteStaffResource(
                staff.getId(),
                staff.getFullName(),
                staff.getEmail(),
                staff.getPhone(),
                staff.getLicenseNumber(),
                staff.getStatus().toApiValue(),
                worksiteStaffQueryService.findAssignedWorksiteIds(staff.getId()),
                staff.getAlertsLast30Days(),
                staff.getDrivingHoursWeek(),
                staff.getCurrentVehicle()
        );
    }

    public static CreateWorksiteStaffCommand toCreateCommandFromResource(CreateWorksiteStaffResource resource) {
        return new CreateWorksiteStaffCommand(
                resource.fullName(),
                resource.email(),
                resource.phone(),
                resource.licenseNumber(),
                StaffStatus.fromApiValue(resource.status())
        );
    }
}
