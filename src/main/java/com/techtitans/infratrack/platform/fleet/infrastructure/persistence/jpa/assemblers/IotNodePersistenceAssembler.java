package com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.assemblers;

import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.IotNode;
import com.techtitans.infratrack.platform.fleet.infrastructure.persistence.jpa.entities.IotNodePersistenceEntity;

public final class IotNodePersistenceAssembler {

    private IotNodePersistenceAssembler() {
    }

    public static IotNode toDomainFromPersistence(IotNodePersistenceEntity entity) {
        if (entity == null) {
            return null;
        }
        var node = new IotNode();
        node.setId(entity.getId());
        node.setMachineryId(entity.getMachineryId());
        node.setNodeIdentifier(entity.getNodeIdentifier());
        node.setFirmwareVersion(entity.getFirmwareVersion());
        node.setBatteryVoltage(entity.getBatteryVoltage());
        node.setConnectionStatus(entity.getConnectionStatus());
        node.setLastSeen(entity.getLastSeen());
        return node;
    }

    public static IotNodePersistenceEntity toPersistenceFromDomain(IotNode node) {
        if (node == null) {
            return null;
        }
        var entity = new IotNodePersistenceEntity();
        if (node.getId() != null) {
            entity.setId(node.getId());
        }
        entity.setMachineryId(node.getMachineryId());
        entity.setNodeIdentifier(node.getNodeIdentifier());
        entity.setFirmwareVersion(node.getFirmwareVersion());
        entity.setBatteryVoltage(node.getBatteryVoltage());
        entity.setConnectionStatus(node.getConnectionStatus());
        entity.setLastSeen(node.getLastSeen());
        return entity;
    }
}
