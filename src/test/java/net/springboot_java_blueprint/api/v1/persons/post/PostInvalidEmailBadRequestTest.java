package net.springboot_java_blueprint.api.v1.persons.post;

import net.springboot_java_blueprint.environment.Fixture;
import net.springboot_java_blueprint.environment.IntegrationTest;
import net.springboot_java_blueprint.environment.TestUsers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class PostInvalidEmailBadRequestTest {
    private static final String ROUTE = "/api/v1/persons";

    @Autowired
    private Fixture fixture;

    @Test
    void personsController_BadRequest() throws Exception {
        fixture.resetDatabase();
        var content = fixture.jsonResources(PostInvalidEmailBadRequestTest.class).readJson("Post_Person_Invalid_Email_Request.json");

        var response = fixture.send(HttpMethod.POST, ROUTE, content, TestUsers.ADMIN);

        assertThat(response.status()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
