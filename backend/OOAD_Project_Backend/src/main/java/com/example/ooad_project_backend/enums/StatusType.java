package com.example.ooad_project_backend.enums;

public enum StatusType {
    Started("Started"),
    Finished("Finished"),
    UNStart("UnStart");

    private final String value;

    StatusType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static StatusType fromString(String value) {
        for (StatusType statusType : StatusType.values()) {
            if (statusType.getValue().equals(value)) {
                return statusType;
            }
        }
        throw new IllegalArgumentException();
    }
}
