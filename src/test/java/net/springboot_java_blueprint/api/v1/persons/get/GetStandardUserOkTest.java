package net.springboot_java_blueprint.api.v1.persons.get;

import net.springboot_java_blueprint.cqrs.commands.CreatePersonResponse;
import net.springboot_java_blueprint.environment.Fixture;
import net.springboot_java_blueprint.environment.IntegrationTest;
import net.springboot_java_blueprint.environment.TestUsers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class GetStandardUserOkTest {
    private static final String ROUTE = "/api/v1/persons";

    @Autowired
    private Fixture fixture;

    @BeforeEach
    void initialize() {
        fixture.resetDatabase();
    }

    @Test
    void personsController_Get_StandardUser_Ok() throws Exception {
        var content = fixture.jsonResources(GetOkTest.class).readJson("Post_Person_Request.json");
        var createdPerson = fixture.send(HttpMethod.POST, ROUTE, content, TestUsers.ADMIN)
                .as(CreatePersonResponse.class);

        var response = fixture.send(HttpMethod.GET, ROUTE + "/" + createdPerson.id(), null, TestUsers.STANDARD);

        assertThat(response.status()).isEqualTo(HttpStatus.OK);
    }
}
