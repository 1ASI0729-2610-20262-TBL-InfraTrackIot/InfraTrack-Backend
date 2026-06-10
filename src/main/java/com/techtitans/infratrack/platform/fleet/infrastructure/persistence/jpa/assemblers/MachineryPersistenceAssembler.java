package com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.assemblers;

import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.Machinery;
import com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.entities.MachineryPersistenceEntity;

public final class MachineryPersistenceAssembler {

    private MachineryPersistenceAssembler() {
    }

    public static Machinery toDomainFromPersistence(MachineryPersistenceEntity entity) {
        if (entity == null) {
            return null;
        }
        var machinery = new Machinery();
        machinery.setId(entity.getId());
        machinery.setOperatorId(entity.getOperatorId());
        machinery.setPlateNumber(entity.getPlateNumber());
        machinery.setModel(entity.getModel());
        machinery.setBrand(entity.getBrand());
        machinery.setFuelType(entity.getFuelType());
        machinery.setTankCapacityLiters(entity.getTankCapacityLiters());
        machinery.setCurrentStatus(entity.getCurrentStatus());
        machinery.setImageUrl(entity.getImageUrl());
        machinery.setCreatedAt(entity.getCreatedAt());
        return machinery;
    }

    public static MachineryPersistenceEntity toPersistenceFromDomain(Machinery machinery) {
        if (machinery == null) {
            return null;
        }
        var entity = new MachineryPersistenceEntity();
        if (machinery.getId() != null) {
            entity.setId(machinery.getId());
        }
        entity.setOperatorId(machinery.getOperatorId());
        entity.setPlateNumber(machinery.getPlateNumber());
        entity.setModel(machinery.getModel());
        entity.setBrand(machinery.getBrand());
        entity.setFuelType(machinery.getFuelType());
        entity.setTankCapacityLiters(machinery.getTankCapacityLiters());
        entity.setCurrentStatus(machinery.getCurrentStatus());
        entity.setImageUrl(machinery.getImageUrl() != null ? machinery.getImageUrl() : "");
        return entity;
    }
}
