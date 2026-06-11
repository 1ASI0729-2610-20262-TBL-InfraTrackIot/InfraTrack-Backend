package com.techtitans.infratrack.platform.monitoring.infrastructure.persistence.jpa.assemblers;

import com.techtitans.infratrack.platform.monitoring.domain.model.aggregates.FleetAlert;
import com.techtitans.infratrack.platform.monitoring.infrastructure.persistence.jpa.entities.FleetAlertPersistenceEntity;

public final class FleetAlertPersistenceAssembler {

    private FleetAlertPersistenceAssembler() {
    }

    public static FleetAlert toDomainFromPersistence(FleetAlertPersistenceEntity entity) {
        if (entity == null) {
            return null;
        }
        var alert = new FleetAlert();
        alert.setId(entity.getId());
        alert.setMachineryId(entity.getMachineryId());
        alert.setType(entity.getType());
        alert.setSeverity(entity.getSeverity());
        alert.setDescription(entity.getDescription());
        alert.setAcknowledged(entity.isAcknowledged());
        alert.setTimestamp(entity.getTimestamp());
        return alert;
    }

    public static FleetAlertPersistenceEntity toPersistenceFromDomain(FleetAlert alert) {
        if (alert == null) {
            return null;
        }
        var entity = new FleetAlertPersistenceEntity();
        if (alert.getId() != null) {
            entity.setId(alert.getId());
        }
        entity.setMachineryId(alert.getMachineryId());
        entity.setType(alert.getType());
        entity.setSeverity(alert.getSeverity());
        entity.setDescription(alert.getDescription());
        entity.setAcknowledged(alert.isAcknowledged());
        entity.setTimestamp(alert.getTimestamp());
        return entity;
    }
}
