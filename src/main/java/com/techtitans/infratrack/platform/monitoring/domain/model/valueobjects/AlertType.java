package com.techtitans.infratrack.platform.monitoring.domain.model.valueobjects;

public enum AlertType {
    FUEL_THEFT,
    IDLE_EXCESS,
    MAINTENANCE,
    GEOFENCE;

    public String toApiValue() {
        return name().toLowerCase();
    }

    public static AlertType fromApiValue(String value) {
        if (value == null || value.isBlank()) {
            return FUEL_THEFT;
        }
        return switch (value.toLowerCase()) {
            case "idle_excess" -> IDLE_EXCESS;
            case "maintenance" -> MAINTENANCE;
            case "geofence" -> GEOFENCE;
            default -> FUEL_THEFT;
        };
    }
}
