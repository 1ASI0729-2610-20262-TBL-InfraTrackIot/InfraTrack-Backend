package com.techtitans.infratrack.platform.fleet.domain.model.valueobjects;

public enum ConnectionStatus {
    ONLINE,
    OFFLINE;

    public String toApiValue() {
        return name().toLowerCase();
    }

    public static ConnectionStatus fromApiValue(String value) {
        return value != null && "offline".equalsIgnoreCase(value) ? OFFLINE : ONLINE;
    }
}
