package com.tcs.user_auth_management.emuns;

public enum Role {
    USER("user"),
    ADMIN("super-admin");
    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
