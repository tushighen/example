package com.golomt.example.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * Role @author Tushig
 */

public enum Role implements GrantedAuthority {

    ROLE_ADMIN, ROLE_CLIENT;

    public String getAuthority() {
        return name();
    }

}