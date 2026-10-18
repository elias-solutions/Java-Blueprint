package net.springboot_java_blueprint.authorization;

import java.util.List;
import java.util.UUID;

public record User(UUID id, String userName, String email, List<String> roles) {
}
