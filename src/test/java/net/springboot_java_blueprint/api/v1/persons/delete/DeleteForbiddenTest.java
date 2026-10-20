package net.springboot_java_blueprint.api.v1.persons.delete;

import net.springboot_java_blueprint.environment.Fixture;
import net.springboot_java_blueprint.environment.IntegrationTest;
import net.springboot_java_blueprint.environment.TestUsers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class DeleteForbiddenTest {
    private static final String ROUTE = "/api/v1/persons";

    @Autowired
    private Fixture fixture;

    @Test
    void personsController_Delete_Forbidden() throws Exception {
        var response = fixture.send(HttpMethod.DELETE, ROUTE + "/" + UUID.randomUUID(), null, TestUsers.READER);
        assertThat(response.status()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
