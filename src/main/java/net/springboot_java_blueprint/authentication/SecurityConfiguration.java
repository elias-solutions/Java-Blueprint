package net.springboot_java_blueprint.authentication;

import net.springboot_java_blueprint.authorization.Roles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    private static final String API = "/api/**";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(resourceServer -> resourceServer.jwt(Customizer.withDefaults()))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, API).hasAnyRole(Roles.READER, Roles.WRITER, Roles.ADMIN)
                        .requestMatchers(HttpMethod.DELETE, API).hasRole(Roles.ADMIN)
                        .requestMatchers(API).hasAnyRole(Roles.WRITER, Roles.ADMIN)
                        .anyRequest().permitAll())
                .build();
    }
}
