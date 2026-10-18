package net.springboot_java_blueprint.authorization;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultUserService implements UserService {
    private static final UUID ADMIN_ID = UUID.fromString("E3D3D338-2053-4CE9-877D-DE4563FE321C");

    @Override
    public Optional<User> getCurrentUser() {
        var role = Roles.ADMIN;
        return Optional.of(new User(ADMIN_ID, role, role + "@" + role + ".ch", List.of(role)));
    }
}
