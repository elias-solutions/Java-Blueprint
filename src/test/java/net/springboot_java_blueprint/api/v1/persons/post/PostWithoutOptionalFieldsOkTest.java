package net.springboot_java_blueprint.api.v1.persons.post;

import net.springboot_java_blueprint.cqrs.commands.CreatePersonResponse;
import net.springboot_java_blueprint.environment.Fixture;
import net.springboot_java_blueprint.environment.IntegrationTest;
import net.springboot_java_blueprint.environment.TestUsers;
import net.springboot_java_blueprint.model.queries.GetPersonResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class PostWithoutOptionalFieldsOkTest {
    private static final String ROUTE = "/api/v1/persons";

    @Autowired
    private Fixture fixture;

    @Test
    void personsController_Ok() throws Exception {
        fixture.resetDatabase();
        var content = fixture.jsonResources(PostWithoutOptionalFieldsOkTest.class).readJson("Post_Person_Minimal_Request.json");
        var response = fixture.send(HttpMethod.POST, ROUTE, content, TestUsers.ADMIN);
        assertThat(response.status()).isEqualTo(HttpStatus.OK);
        var createPersonResponse = response.as(CreatePersonResponse.class);

        response = fixture.send(HttpMethod.GET, ROUTE + "/" + createPersonResponse.id(), null, TestUsers.ADMIN);
        assertThat(response.status()).isEqualTo(HttpStatus.OK);

        var person = response.as(GetPersonResponse.class);
        assertThat(person.birthday()).isNull();
        assertThat(person.gender()).isNull();
        assertThat(person.description()).isNull();
        assertThat(person.email()).isNull();
        assertThat(person.phone()).isNull();
        assertThat(person.type()).isNull();
    }
}
