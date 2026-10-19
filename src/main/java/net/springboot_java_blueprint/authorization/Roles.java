package net.springboot_java_blueprint.authorization;

/**
 * Realm roles issued by Keycloak in the {@code roles} claim.
 */
public final class Roles {
    public static final String STANDARD = "standard";
    public static final String ADMIN = "admin";

    private Roles() {
    }
}
