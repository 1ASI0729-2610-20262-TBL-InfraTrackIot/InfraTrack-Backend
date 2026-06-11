package com.techtitans.infratrack.platform.monitoring.domain.model.valueobjects;

public enum AlertSeverity {
    CRITICAL,
    WARNING;

    public String toApiValue() {
        return name().toLowerCase();
    }

    public static AlertSeverity fromApiValue(String value) {
        return value != null && "critical".equalsIgnoreCase(value) ? CRITICAL : WARNING;
    }
}
