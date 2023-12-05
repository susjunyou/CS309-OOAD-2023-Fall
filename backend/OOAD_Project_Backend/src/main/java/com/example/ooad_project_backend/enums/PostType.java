package com.example.ooad_project_backend.enums;

public enum PostType {
    QUESTION("Question"),

    SKILL_SHARE("Skill Share"),

    ANNOUNCEMENT("Announcement");

    private final String value;

    PostType(String value) {
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
