package com.techtitans.infratrack.platform.fleet.domain.model.valueobjects;

public enum OperatorStatus {
    ACTIVE,
    INACTIVE;

    public String toApiValue() {
        return name().toLowerCase();
    }

    public static OperatorStatus fromApiValue(String value) {
        return value != null && "inactive".equalsIgnoreCase(value) ? INACTIVE : ACTIVE;
    }
}
