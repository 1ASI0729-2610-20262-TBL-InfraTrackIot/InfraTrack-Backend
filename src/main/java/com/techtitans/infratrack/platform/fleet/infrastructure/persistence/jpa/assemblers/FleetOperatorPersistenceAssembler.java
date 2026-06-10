package com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.assemblers;

import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.FleetOperator;
import com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.entities.FleetOperatorPersistenceEntity;

public final class FleetOperatorPersistenceAssembler {

    private FleetOperatorPersistenceAssembler() {
    }

    public static FleetOperator toDomainFromPersistence(FleetOperatorPersistenceEntity entity) {
        if (entity == null) {
            return null;
        }
        var operator = new FleetOperator();
        operator.setId(entity.getId());
        operator.setUserId(entity.getUserId());
        operator.setFullName(entity.getFullName());
        operator.setLicenseNumber(entity.getLicenseNumber());
        operator.setPhone(entity.getPhone());
        operator.setStatus(entity.getStatus());
        return operator;
    }

    public static FleetOperatorPersistenceEntity toPersistenceFromDomain(FleetOperator operator) {
        if (operator == null) {
            return null;
        }
        var entity = new FleetOperatorPersistenceEntity();
        if (operator.getId() != null) {
            entity.setId(operator.getId());
        }
        entity.setUserId(operator.getUserId());
        entity.setFullName(operator.getFullName());
        entity.setLicenseNumber(operator.getLicenseNumber());
        entity.setPhone(operator.getPhone());
        entity.setStatus(operator.getStatus());
        return entity;
    }
}
