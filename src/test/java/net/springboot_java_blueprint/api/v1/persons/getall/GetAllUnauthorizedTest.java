package net.springboot_java_blueprint.api.v1.persons.getall;

import net.springboot_java_blueprint.environment.Fixture;
import net.springboot_java_blueprint.environment.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class GetAllUnauthorizedTest {
    private static final String ROUTE = "/api/v1/persons";

    @Autowired
    private Fixture fixture;

    @Test
    void personsController_GetAll_Unauthorized() throws Exception {
        var response = fixture.send(HttpMethod.GET, ROUTE, null, null);
        assertThat(response.status()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
