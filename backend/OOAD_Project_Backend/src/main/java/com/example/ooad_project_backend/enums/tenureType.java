package com.example.ooad_project_backend.enums;

public enum tenureType {

    CHAIR_PROFESSOR("chair_professor"),
    PROFESSOR("professor"),
    ASSOCIATE_PROFESSOR("associate_professor"),
    ASSISTANT_PROFESSOR("assistant_professor"),
    LECTURER("lecturer");

    private final String value;

    tenureType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static tenureType fromString(String value) {
        for (tenureType type : tenureType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException();

    }

}
