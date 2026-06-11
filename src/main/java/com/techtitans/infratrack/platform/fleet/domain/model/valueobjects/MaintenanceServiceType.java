package com.techtitans.infratrack.platform.fleet.domain.model.valueobjects;

public enum MaintenanceServiceType {
    OIL_CHANGE,
    FILTER,
    TIRES,
    GENERAL;

    public String toApiValue() {
        return name().toLowerCase();
    }

    public static MaintenanceServiceType fromApiValue(String value) {
        if (value == null || value.isBlank()) {
            return GENERAL;
        }
        return switch (value.toLowerCase()) {
            case "oil_change" -> OIL_CHANGE;
            case "filter" -> FILTER;
            case "tires" -> TIRES;
            default -> GENERAL;
        };
    }
}
