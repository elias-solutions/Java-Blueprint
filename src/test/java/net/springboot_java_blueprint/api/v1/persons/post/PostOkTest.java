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

import static net.springboot_java_blueprint.assertions.EquivalencyAssertions.assertEquivalentIgnoringIdentity;
import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class PostOkTest {
    private static final String ROUTE = "/api/v1/persons";

    @Autowired
    private Fixture fixture;

    @Test
    void personsController_Ok() throws Exception {
        fixture.resetDatabase();
        var jsonResources = fixture.jsonResources(PostOkTest.class);
        var content = jsonResources.readJson("Post_Person_Request.json");
        var response = fixture.send(HttpMethod.POST, ROUTE, content, TestUsers.ADMIN);
        assertThat(response.status()).isEqualTo(HttpStatus.OK);
        var createPersonResponse = response.as(CreatePersonResponse.class);

        response = fixture.send(HttpMethod.GET, ROUTE + "/" + createPersonResponse.id(), null, TestUsers.ADMIN);
        assertThat(response.status()).isEqualTo(HttpStatus.OK);

        var dbPerson = response.as(GetPersonResponse.class);
        var expectedPerson = jsonResources.readObject("Post_Person_Response.json", GetPersonResponse.class);
        assertEquivalentIgnoringIdentity(dbPerson, expectedPerson);
    }
}
