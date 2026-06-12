package com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.assemblers;

import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.WorksiteStaff;
import com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.entities.WorksiteStaffPersistenceEntity;

public final class WorksiteStaffPersistenceAssembler {

    private WorksiteStaffPersistenceAssembler() {
    }

    public static WorksiteStaff toDomainFromPersistence(WorksiteStaffPersistenceEntity entity) {
        var staff = new WorksiteStaff();
        staff.setId(entity.getId());
        staff.setFullName(entity.getFullName());
        staff.setEmail(entity.getEmail());
        staff.setPhone(entity.getPhone());
        staff.setLicenseNumber(entity.getLicenseNumber());
        staff.setStatus(entity.getStatus());
        staff.setAlertsLast30Days(entity.getAlertsLast30Days());
        staff.setDrivingHoursWeek(entity.getDrivingHoursWeek());
        staff.setCurrentVehicle(entity.getCurrentVehicle());
        return staff;
    }

    public static WorksiteStaffPersistenceEntity toPersistenceFromDomain(WorksiteStaff staff) {
        var entity = new WorksiteStaffPersistenceEntity();
        entity.setId(staff.getId());
        entity.setFullName(staff.getFullName());
        entity.setEmail(staff.getEmail());
        entity.setPhone(staff.getPhone());
        entity.setLicenseNumber(staff.getLicenseNumber());
        entity.setStatus(staff.getStatus());
        entity.setAlertsLast30Days(staff.getAlertsLast30Days());
        entity.setDrivingHoursWeek(staff.getDrivingHoursWeek());
        entity.setCurrentVehicle(staff.getCurrentVehicle());
        return entity;
    }
}
