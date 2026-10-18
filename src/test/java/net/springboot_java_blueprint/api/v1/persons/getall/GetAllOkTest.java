package net.springboot_java_blueprint.api.v1.persons.getall;

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
import tools.jackson.core.type.TypeReference;

import java.util.List;

import static net.springboot_java_blueprint.assertions.EquivalencyAssertions.assertEquivalentIgnoringIdentity;
import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class GetAllOkTest {
    private static final String ROUTE = "/api/v1/persons";
    private static final TypeReference<List<GetPersonResponse>> PERSON_LIST = new TypeReference<>() {
    };

    @Autowired
    private Fixture fixture;

    private JsonResourceProvider jsonResources;

    @BeforeEach
    void initialize() {
        fixture.resetDatabase();
        jsonResources = fixture.jsonResources(GetAllOkTest.class);
    }

    @Test
    void personsController_GetAll_Ok() throws Exception {
        var content = jsonResources.readJson("Post_Person_Request.json");
        var response = fixture.send(HttpMethod.POST, ROUTE, content, TestUsers.ADMIN);
        assertThat(response.status()).isEqualTo(HttpStatus.OK);

        response = fixture.send(HttpMethod.GET, ROUTE, null, TestUsers.ADMIN);
        assertThat(response.status()).isEqualTo(HttpStatus.OK);

        var result = response.as(PERSON_LIST);
        var expected = jsonResources.readObject("Get_Person_Response.json", PERSON_LIST);
        assertEquivalentIgnoringIdentity(result, expected);
    }
}
