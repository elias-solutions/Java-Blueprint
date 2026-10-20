package net.springboot_java_blueprint.environment;

import java.util.List;
import java.util.UUID;

public record TestUser(UUID id, String userName, List<String> roles) {
}
