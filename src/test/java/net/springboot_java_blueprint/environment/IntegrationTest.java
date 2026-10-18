package net.springboot_java_blueprint.environment;

import net.springboot_java_blueprint.authorization.UserService;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Boots the whole application on a random port with a mocked {@link UserService}.
 * All annotated test classes share one cached application context (like an xUnit collection fixture).
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Tag("integration")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "blueprint.rate-limit.permit-limit=100000")
@Import(Fixture.class)
@MockitoBean(types = UserService.class)
public @interface IntegrationTest {
}
