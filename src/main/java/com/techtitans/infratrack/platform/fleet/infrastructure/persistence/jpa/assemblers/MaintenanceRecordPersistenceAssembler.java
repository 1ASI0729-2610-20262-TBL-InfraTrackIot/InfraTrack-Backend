package com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.assemblers;

import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.MaintenanceRecord;
import com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.entities.MaintenanceRecordPersistenceEntity;

public final class MaintenanceRecordPersistenceAssembler {

    private MaintenanceRecordPersistenceAssembler() {
    }

    public static MaintenanceRecord toDomainFromPersistence(MaintenanceRecordPersistenceEntity entity) {
        if (entity == null) {
            return null;
        }
        var record = new MaintenanceRecord();
        record.setId(entity.getId());
        record.setMachineryId(entity.getMachineryId());
        record.setServiceType(entity.getServiceType());
        record.setDescription(entity.getDescription());
        record.setCostPen(entity.getCostPen());
        record.setEngineHoursAtService(entity.getEngineHoursAtService());
        record.setServiceDate(entity.getServiceDate());
        record.setNextServiceDate(entity.getNextServiceDate());
        return record;
    }

    public static MaintenanceRecordPersistenceEntity toPersistenceFromDomain(MaintenanceRecord record) {
        if (record == null) {
            return null;
        }
        var entity = new MaintenanceRecordPersistenceEntity();
        if (record.getId() != null) {
            entity.setId(record.getId());
        }
        entity.setMachineryId(record.getMachineryId());
        entity.setServiceType(record.getServiceType());
        entity.setDescription(record.getDescription());
        entity.setCostPen(record.getCostPen());
        entity.setEngineHoursAtService(record.getEngineHoursAtService());
        entity.setServiceDate(record.getServiceDate());
        entity.setNextServiceDate(record.getNextServiceDate());
        return entity;
    }
}
