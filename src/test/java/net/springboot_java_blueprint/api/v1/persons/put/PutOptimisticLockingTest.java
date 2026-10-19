package net.springboot_java_blueprint.api.v1.persons.put;

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
import org.springframework.http.ProblemDetail;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class PutOptimisticLockingTest {
    private static final String ROUTE = "/api/v1/persons";

    @Autowired
    private Fixture fixture;

    private JsonResourceProvider jsonResources;

    @BeforeEach
    void initialize() {
        fixture.resetDatabase();
        jsonResources = fixture.jsonResources(PutOptimisticLockingTest.class);
    }

    @Test
    void personsController_Ok() throws Exception {
        var content = jsonResources.readJson("Post_Person_Request.json");
        var response = fixture.send(HttpMethod.POST, ROUTE, content, TestUsers.ADMIN);
        assertThat(response.status()).isEqualTo(HttpStatus.OK);
        var createdPerson = response.as(CreatePersonResponse.class);

        var updateRequest = putPersonRequest(createdPerson.id(), UUID.randomUUID());
        response = fixture.send(HttpMethod.PUT, ROUTE + "/" + createdPerson.id(), fixture.toJson(updateRequest), TestUsers.ADMIN);
        assertThat(response.status()).isEqualTo(HttpStatus.BAD_REQUEST);

        var problemDetail = response.as(ProblemDetail.class);
        assertThat(problemDetail.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(problemDetail.getTitle()).isEqualTo("BadRequest - Entity version conflict");
        assertThat(problemDetail.getDetail()).isEqualTo("Entity has been updated through other user.");
    }

    @Test
    void personsController_StaleVersionAfterUpdate_BadRequest() throws Exception {
        var content = jsonResources.readJson("Post_Person_Request.json");
        var response = fixture.send(HttpMethod.POST, ROUTE, content, TestUsers.ADMIN);
        assertThat(response.status()).isEqualTo(HttpStatus.OK);
        var createdPerson = response.as(CreatePersonResponse.class);

        response = fixture.send(HttpMethod.GET, ROUTE + "/" + createdPerson.id(), null, TestUsers.ADMIN);
        assertThat(response.status()).isEqualTo(HttpStatus.OK);
        var person = response.as(GetPersonResponse.class);

        var updateRequest = fixture.toJson(putPersonRequest(person.id(), person.version()));
        response = fixture.send(HttpMethod.PUT, ROUTE + "/" + person.id(), updateRequest, TestUsers.ADMIN);
        assertThat(response.status()).isEqualTo(HttpStatus.OK);

        response = fixture.send(HttpMethod.PUT, ROUTE + "/" + person.id(), updateRequest, TestUsers.ADMIN);
        assertThat(response.status()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private GetPersonResponse putPersonRequest(UUID id, UUID version) {
        var template = jsonResources.readObject("Put_Person_Request.json", GetPersonResponse.class);
        return new GetPersonResponse(
                id,
                template.firstName(),
                template.lastName(),
                template.birthday(),
                template.gender(),
                template.description(),
                template.email(),
                template.phone(),
                template.type(),
                template.addresses(),
                template.createdId(),
                template.createdDate(),
                template.modifiedId(),
                template.modifiedDate(),
                version);
    }
}
