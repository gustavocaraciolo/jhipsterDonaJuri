package com.br.discovery.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String COORDENADOR = "ROLE_COORDENADOR";

    public static final String ADVOGADO = "ROLE_ADVOGADO";

    public static final String CLIENTE = "ROLE_CLIENTE";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {
    }
}
