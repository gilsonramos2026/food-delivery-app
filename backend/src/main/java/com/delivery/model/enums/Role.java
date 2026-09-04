// Role.java
package com.delivery.model.enums;

public enum Role {
    CLIENT("ROLE_CLIENT"),
    COURIER("ROLE_COURIER"),
    ADMIN("ROLE_ADMIN");

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }
}
