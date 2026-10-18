package net.springboot_java_blueprint.environment;

import net.springboot_java_blueprint.authorization.Roles;
import net.springboot_java_blueprint.authorization.User;

import java.util.List;
import java.util.UUID;

public final class TestUsers {
    public static final User ADMIN = new User(
            UUID.fromString("E3D3D338-2053-4CE9-877D-DE4563FE321C"),
            Roles.ADMIN, Roles.ADMIN + "@" + Roles.ADMIN + ".ch", List.of(Roles.ADMIN));

    public static final User STANDARD = new User(
            UUID.fromString("E3D3D338-2053-4CE9-877D-DE4563FE321D"),
            Roles.STANDARD, Roles.STANDARD + "@" + Roles.STANDARD + ".ch", List.of(Roles.STANDARD));

    private TestUsers() {
    }
}
