package com.techtitans.infratrack.platform.fleet.domain.model.valueobjects;

public enum FuelType {
    DIESEL,
    GASOLINE;

    public String toApiValue() {
        return name().toLowerCase();
    }

    public static FuelType fromApiValue(String value) {
        if (value == null || value.isBlank()) {
            return DIESEL;
        }
        return "gasoline".equalsIgnoreCase(value) ? GASOLINE : DIESEL;
    }
}
