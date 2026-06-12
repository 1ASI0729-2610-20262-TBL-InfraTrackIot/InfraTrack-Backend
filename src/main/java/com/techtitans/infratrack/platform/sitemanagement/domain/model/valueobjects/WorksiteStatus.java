package com.techtitans.infratrack.platform.sitemanagement.domain.model.valueobjects;

public enum WorksiteStatus {
    ACTIVE,
    FINISHED;

    public String toApiValue() {
        return name().toLowerCase();
    }

    public static WorksiteStatus fromApiValue(String value) {
        if (value == null || value.isBlank()) {
            return ACTIVE;
        }
        return "finished".equalsIgnoreCase(value) ? FINISHED : ACTIVE;
    }
}
