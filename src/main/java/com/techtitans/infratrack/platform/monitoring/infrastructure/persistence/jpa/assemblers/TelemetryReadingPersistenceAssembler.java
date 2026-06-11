package com.techtitans.infratrack.platform.monitoring.infrastructure.persistence.jpa.assemblers;

import com.techtitans.infratrack.platform.monitoring.domain.model.aggregates.TelemetryReading;
import com.techtitans.infratrack.platform.monitoring.infrastructure.persistence.jpa.entities.TelemetryReadingPersistenceEntity;

public final class TelemetryReadingPersistenceAssembler {

    private TelemetryReadingPersistenceAssembler() {
    }

    public static TelemetryReading toDomainFromPersistence(TelemetryReadingPersistenceEntity entity) {
        if (entity == null) {
            return null;
        }
        var reading = new TelemetryReading();
        reading.setId(entity.getId());
        reading.setNodeId(entity.getNodeId());
        reading.setFuelLevel(entity.getFuelLevel());
        reading.setFuelLevelPct(entity.getFuelLevelPct());
        reading.setLatitude(entity.getLatitude());
        reading.setLongitude(entity.getLongitude());
        reading.setEngineHours(entity.getEngineHours());
        reading.setSpeedKmh(entity.getSpeedKmh());
        reading.setEngineOn(entity.getEngineOn());
        reading.setRecordedAt(entity.getRecordedAt());
        return reading;
    }

    public static TelemetryReadingPersistenceEntity toPersistenceFromDomain(TelemetryReading reading) {
        if (reading == null) {
            return null;
        }
        var entity = new TelemetryReadingPersistenceEntity();
        if (reading.getId() != null) {
            entity.setId(reading.getId());
        }
        entity.setNodeId(reading.getNodeId());
        entity.setFuelLevel(reading.getFuelLevel());
        entity.setFuelLevelPct(reading.getFuelLevelPct());
        entity.setLatitude(reading.getLatitude());
        entity.setLongitude(reading.getLongitude());
        entity.setEngineHours(reading.getEngineHours());
        entity.setSpeedKmh(reading.getSpeedKmh());
        entity.setEngineOn(reading.getEngineOn());
        entity.setRecordedAt(reading.getRecordedAt());
        return entity;
    }
}
