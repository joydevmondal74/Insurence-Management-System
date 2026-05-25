package com.insurence.management.enums;

public enum PolicyType {
    AUTO("Automobile"),
    HOME("Homeowners"),
    LIFE("Life Insurance"),
    HEALTH("Health Insurance");

    private final String displayName;

    PolicyType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
