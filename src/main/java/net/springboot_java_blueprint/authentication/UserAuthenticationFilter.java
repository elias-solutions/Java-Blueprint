package net.springboot_java_blueprint.authentication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.springboot_java_blueprint.authorization.User;
import net.springboot_java_blueprint.authorization.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class UserAuthenticationFilter extends OncePerRequestFilter {
    private static final String ROLE_PREFIX = "ROLE_";

    private final UserService userService;

    public UserAuthenticationFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        userService.getCurrentUser().ifPresent(this::authenticate);
        chain.doFilter(request, response);
    }

    private void authenticate(User user) {
        var authorities = user.roles().stream()
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role))
                .toList();
        var authentication = UsernamePasswordAuthenticationToken.authenticated(user.userName(), null, authorities);

        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}
