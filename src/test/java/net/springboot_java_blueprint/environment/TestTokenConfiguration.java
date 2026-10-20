package net.springboot_java_blueprint.environment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;

@TestConfiguration(proxyBeanMethods = false)
public class TestTokenConfiguration {

    @Bean(destroyMethod = "close")
    TestTokenIssuer testTokenIssuer(
            @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") String issuer,
            @Value("${spring.security.oauth2.resourceserver.jwt.audiences}") String audience) {
        return new TestTokenIssuer(issuer, audience);
    }

    @Bean
    DynamicPropertyRegistrar jwkSetUriRegistrar(TestTokenIssuer issuer) {
        return registry -> registry.add("spring.security.oauth2.resourceserver.jwt.jwk-set-uri", issuer::jwkSetUri);
    }
}
