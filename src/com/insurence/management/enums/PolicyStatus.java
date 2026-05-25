package com.insurence.management.enums;

public enum PolicyStatus {

    ACTIVE("Active"),
    EXPIRED("Expired"),
    CANCELLED("Cencelled");

    private final String displayStatus;

    PolicyStatus(String displayStatus) {
        this.displayStatus = displayStatus;
    }

    public String getDisplayStatus() {
        return displayStatus;
    }

    @Override
    public String toString() {
        return displayStatus;
    }

}
