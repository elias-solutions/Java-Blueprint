package net.springboot_java_blueprint.authorization;

import java.util.Optional;

public interface UserService {
    Optional<User> getCurrentUser();
}
