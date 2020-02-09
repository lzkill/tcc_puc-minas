package com.lzkill.gateway.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    /** Gestor de qualidade */
    public static final String SGQ = "ROLE_SGQ";

    private AuthoritiesConstants() {
    }
}
