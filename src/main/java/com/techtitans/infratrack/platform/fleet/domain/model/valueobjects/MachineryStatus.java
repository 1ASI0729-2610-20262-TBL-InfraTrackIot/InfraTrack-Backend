package com.techtitans.infratrack.platform.fleet.domain.model.valueobjects;

/**
 * Operational status of a machinery unit in the fleet.
 */
public enum MachineryStatus {
    ACTIVE,
    INACTIVE,
    MAINTENANCE;

    public String toApiValue() {
        return name().toLowerCase();
    }

    public static MachineryStatus fromApiValue(String value) {
        if (value == null || value.isBlank()) {
            return ACTIVE;
        }
        return switch (value.toLowerCase()) {
            case "inactive" -> INACTIVE;
            case "maintenance" -> MAINTENANCE;
            default -> ACTIVE;
        };
    }
}
