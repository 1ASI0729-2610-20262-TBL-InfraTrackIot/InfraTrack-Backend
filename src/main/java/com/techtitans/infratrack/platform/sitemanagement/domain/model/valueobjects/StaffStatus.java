package com.techtitans.infratrack.platform.sitemanagement.domain.model.valueobjects;

public enum StaffStatus {
    ACTIVE,
    INACTIVE;

    public String toApiValue() {
        return name().toLowerCase();
    }

    public static StaffStatus fromApiValue(String value) {
        if (value == null || value.isBlank()) {
            return ACTIVE;
        }
        return "inactive".equalsIgnoreCase(value) ? INACTIVE : ACTIVE;
    }
}
