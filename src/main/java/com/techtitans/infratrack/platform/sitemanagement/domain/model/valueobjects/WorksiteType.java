package com.techtitans.infratrack.platform.sitemanagement.domain.model.valueobjects;

public enum WorksiteType {
    ROAD,
    BUILDING,
    WAREHOUSE;

    public String toApiValue() {
        return name().toLowerCase();
    }

    public static WorksiteType fromApiValue(String value) {
        if (value == null || value.isBlank()) {
            return ROAD;
        }
        return switch (value.toLowerCase()) {
            case "building" -> BUILDING;
            case "warehouse" -> WAREHOUSE;
            default -> ROAD;
        };
    }
}
