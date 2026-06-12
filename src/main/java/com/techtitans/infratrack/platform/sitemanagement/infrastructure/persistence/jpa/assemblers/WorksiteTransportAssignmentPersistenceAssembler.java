package com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.assemblers;

import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.WorksiteTransportAssignment;
import com.techtitans.infratrack.platform.sitemanagement.infrastructure.persistence.jpa.entities.WorksiteTransportAssignmentPersistenceEntity;

public final class WorksiteTransportAssignmentPersistenceAssembler {

    private WorksiteTransportAssignmentPersistenceAssembler() {
    }

    public static WorksiteTransportAssignment toDomainFromPersistence(WorksiteTransportAssignmentPersistenceEntity entity) {
        var assignment = new WorksiteTransportAssignment();
        assignment.setId(entity.getId());
        assignment.setWorksiteId(entity.getWorksiteId());
        assignment.setMachineryId(entity.getMachineryId());
        assignment.setGpsLabel(entity.getGpsLabel());
        return assignment;
    }

    public static WorksiteTransportAssignmentPersistenceEntity toPersistenceFromDomain(WorksiteTransportAssignment assignment) {
        var entity = new WorksiteTransportAssignmentPersistenceEntity();
        entity.setId(assignment.getId());
        entity.setWorksiteId(assignment.getWorksiteId());
        entity.setMachineryId(assignment.getMachineryId());
        entity.setGpsLabel(assignment.getGpsLabel());
        return entity;
    }
}
