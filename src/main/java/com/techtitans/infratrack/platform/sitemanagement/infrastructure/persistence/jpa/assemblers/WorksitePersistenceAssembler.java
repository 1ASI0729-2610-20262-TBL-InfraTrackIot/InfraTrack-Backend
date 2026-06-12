package com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.assemblers;

import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.Worksite;
import com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.entities.WorksitePersistenceEntity;

public final class WorksitePersistenceAssembler {

    private WorksitePersistenceAssembler() {
    }

    public static Worksite toDomainFromPersistence(WorksitePersistenceEntity entity) {
        var worksite = new Worksite();
        worksite.setId(entity.getId());
        worksite.setName(entity.getName());
        worksite.setCity(entity.getCity());
        worksite.setType(entity.getType());
        worksite.setStatus(entity.getStatus());
        worksite.setAddress(entity.getAddress());
        worksite.setLeadEngineer(entity.getLeadEngineer());
        worksite.setLatitude(entity.getLatitude());
        worksite.setLongitude(entity.getLongitude());
        return worksite;
    }

    public static WorksitePersistenceEntity toPersistenceFromDomain(Worksite worksite) {
        var entity = new WorksitePersistenceEntity();
        entity.setId(worksite.getId());
        entity.setName(worksite.getName());
        entity.setCity(worksite.getCity());
        entity.setType(worksite.getType());
        entity.setStatus(worksite.getStatus());
        entity.setAddress(worksite.getAddress());
        entity.setLeadEngineer(worksite.getLeadEngineer());
        entity.setLatitude(worksite.getLatitude());
        entity.setLongitude(worksite.getLongitude());
        return entity;
    }
}
