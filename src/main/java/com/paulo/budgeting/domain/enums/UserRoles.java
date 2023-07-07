package com.paulo.budgeting.domain.enums;

public enum UserRoles {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADIN");

    private String value;
    UserRoles(String value) {
        this.value = value;
    }

    public String getRole() {
        return value;
    }
}
