package com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates;

import com.techtitans.infratrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.commands.CreateWorksiteStaffCommand;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.valueobjects.StaffStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
public class WorksiteStaff extends AbstractDomainAggregateRoot<WorksiteStaff> {

    @Setter
    private Long id;
    @Setter
    private String fullName;
    @Setter
    private String email;
    @Setter
    private String phone;
    @Setter
    private String licenseNumber;
    @Setter
    private StaffStatus status;
    @Setter
    private Integer alertsLast30Days;
    @Setter
    private Integer drivingHoursWeek;
    @Setter
    private String currentVehicle;

    public WorksiteStaff() {
        this.status = StaffStatus.ACTIVE;
        this.alertsLast30Days = 0;
        this.drivingHoursWeek = 0;
    }

    public WorksiteStaff(CreateWorksiteStaffCommand command) {
        this.fullName = command.fullName();
        this.email = command.email();
        this.phone = command.phone() != null ? command.phone() : "";
        this.licenseNumber = command.licenseNumber() != null ? command.licenseNumber() : "";
        this.status = command.status() != null ? command.status() : StaffStatus.ACTIVE;
        this.alertsLast30Days = 0;
        this.drivingHoursWeek = 0;
    }
}
