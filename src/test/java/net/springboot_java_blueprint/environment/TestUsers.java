package net.springboot_java_blueprint.environment;

import net.springboot_java_blueprint.authorization.Roles;

import java.util.List;
import java.util.UUID;

public final class TestUsers {
    public static final TestUser ADMIN = new TestUser(
            UUID.fromString("E3D3D338-2053-4CE9-877D-DE4563FE321C"), "admin", List.of(Roles.ADMIN));

    public static final TestUser STANDARD = new TestUser(
            UUID.fromString("E3D3D338-2053-4CE9-877D-DE4563FE321D"), "user", List.of(Roles.STANDARD));

    private TestUsers() {
    }
}
