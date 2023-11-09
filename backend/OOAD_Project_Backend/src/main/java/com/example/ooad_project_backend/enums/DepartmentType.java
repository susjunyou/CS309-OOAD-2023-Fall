package com.example.ooad_project_backend.enums;

public enum DepartmentType {
    COMPUTER_SCIENCE("Computer Science"),
    ELECTRICAL_ENGINEERING("Electrical Engineering"),
    MECHANICAL_ENGINEERING("Mechanical Engineering"),
    CHEMISTRY("Chemistry"),
    BIOLOGY("Biology"),
    MATHEMATICS("Mathematics"),
    PHYSICS("Physics"),
    GEOGRAPHY("Geography"),
    ;

    private final String value;

    DepartmentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static DepartmentType fromString(String value) {
        for (DepartmentType departmentType : DepartmentType.values()) {
            if (departmentType.getValue().equals(value)) {
                return departmentType;
            }
        }
        throw new IllegalArgumentException("Invalid department type: " + value);
    }
}
