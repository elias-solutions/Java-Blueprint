package net.springboot_java_blueprint.api.v1.persons.get;

import net.springboot_java_blueprint.cqrs.commands.CreatePersonResponse;
import net.springboot_java_blueprint.environment.Fixture;
import net.springboot_java_blueprint.environment.IntegrationTest;
import net.springboot_java_blueprint.environment.JsonResourceProvider;
import net.springboot_java_blueprint.environment.TestUsers;
import net.springboot_java_blueprint.model.queries.GetPersonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static net.springboot_java_blueprint.assertions.EquivalencyAssertions.assertEquivalentIgnoringIdentity;
import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class GetOkTest {
    private static final String ROUTE = "/api/v1/persons";

    @Autowired
    private Fixture fixture;

    private JsonResourceProvider jsonResources;

    @BeforeEach
    void initialize() {
        fixture.resetDatabase();
        jsonResources = fixture.jsonResources(GetOkTest.class);
    }

    @Test
    void personsController_Get_Ok() throws Exception {
        var content = jsonResources.readJson("Post_Person_Request.json");
        var response = fixture.send(HttpMethod.POST, ROUTE, content, TestUsers.ADMIN);
        assertThat(response.status()).isEqualTo(HttpStatus.OK);
        var createdPerson = response.as(CreatePersonResponse.class);

        response = fixture.send(HttpMethod.GET, ROUTE + "/" + createdPerson.id(), null, TestUsers.ADMIN);
        assertThat(response.status()).isEqualTo(HttpStatus.OK);

        var expected = jsonResources.readObject("Get_Person_Response.json", GetPersonResponse.class);
        var result = response.as(GetPersonResponse.class);
        assertEquivalentIgnoringIdentity(result, expected);
    }
}
