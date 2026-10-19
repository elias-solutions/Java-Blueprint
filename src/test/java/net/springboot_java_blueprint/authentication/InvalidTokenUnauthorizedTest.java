package net.springboot_java_blueprint.authentication;

import net.springboot_java_blueprint.environment.Fixture;
import net.springboot_java_blueprint.environment.IntegrationTest;
import net.springboot_java_blueprint.environment.TestUsers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class InvalidTokenUnauthorizedTest {
    private static final String ROUTE = "/api/v1/persons";

    @Autowired
    private Fixture fixture;

    @Test
    void foreignSignature_Unauthorized() throws Exception {
        assertUnauthorized(fixture.tokens().issueWithForeignKey(TestUsers.ADMIN));
    }

    @Test
    void foreignIssuer_Unauthorized() throws Exception {
        assertUnauthorized(fixture.tokens().issue(TestUsers.ADMIN,
                claims -> claims.issuer("http://localhost:8180/realms/other")));
    }

    @Test
    void foreignAudience_Unauthorized() throws Exception {
        assertUnauthorized(fixture.tokens().issue(TestUsers.ADMIN, claims -> claims.audience("account")));
    }

    @Test
    void expiredToken_Unauthorized() throws Exception {
        var expired = Date.from(Instant.now().minusSeconds(3600));
        assertUnauthorized(fixture.tokens().issue(TestUsers.ADMIN, claims -> claims.expirationTime(expired)));
    }

    private void assertUnauthorized(String accessToken) throws Exception {
        var response = fixture.sendWithToken(HttpMethod.GET, ROUTE, null, accessToken);
        assertThat(response.status()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
