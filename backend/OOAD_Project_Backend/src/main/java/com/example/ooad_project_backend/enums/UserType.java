package com.example.ooad_project_backend.enums;

public enum UserType {
    ADMIN("ADMIN"),
    USER("USER");

    private final String value;

    UserType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static UserType fromString(String value) {
        for (UserType userType : UserType.values()) {
            if (userType.getValue().equals(value)) {
                return userType;
            }
        }
        throw new IllegalArgumentException("Invalid user type: " + value);
    }
}
