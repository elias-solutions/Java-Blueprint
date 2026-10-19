package net.springboot_java_blueprint.environment;

import java.util.List;
import java.util.UUID;

/** A Keycloak user as the access token describes it: {@code id} becomes the {@code sub} claim. */
public record TestUser(UUID id, String userName, List<String> roles) {
}
