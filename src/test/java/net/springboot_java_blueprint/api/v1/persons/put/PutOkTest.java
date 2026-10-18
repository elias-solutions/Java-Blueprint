package net.springboot_java_blueprint.api.v1.persons.put;

import net.springboot_java_blueprint.cqrs.commands.CreatePersonResponse;
import net.springboot_java_blueprint.environment.Fixture;
import net.springboot_java_blueprint.environment.IntegrationTest;
import net.springboot_java_blueprint.environment.JsonResourceProvider;
import net.springboot_java_blueprint.environment.TestUsers;
import net.springboot_java_blueprint.model.commands.UpdateAddressRequest;
import net.springboot_java_blueprint.model.commands.UpdatePersonRequest;
import net.springboot_java_blueprint.model.queries.GetAddressResponse;
import net.springboot_java_blueprint.model.queries.GetPersonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static net.springboot_java_blueprint.assertions.EquivalencyAssertions.assertEquivalentIgnoringIdentity;
import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class PutOkTest {
    private static final String ROUTE = "/api/v1/persons";
    private static final OffsetDateTime CREATED_DATE = OffsetDateTime.parse("2023-10-10T14:30:00+00:00");
    private static final OffsetDateTime MODIFIED_DATE = OffsetDateTime.parse("2023-11-10T14:30:00+00:00");
    private static final UUID EMPTY_ID = new UUID(0, 0);

    @Autowired
    private Fixture fixture;

    private JsonResourceProvider jsonResources;

    @BeforeEach
    void initialize() {
        fixture.resetDatabase();
        jsonResources = fixture.jsonResources(PutOkTest.class);
    }

    @Test
    void personsController_Ok() throws Exception {
        var content = jsonResources.readJson("Post_Person_Request.json");
        var response = fixture.send(HttpMethod.POST, ROUTE, content, TestUsers.ADMIN);
        assertThat(response.status()).isEqualTo(HttpStatus.OK);
        var createPersonResponse = response.as(CreatePersonResponse.class);

        response = fixture.send(HttpMethod.GET, ROUTE + "/" + createPersonResponse.id(), null, TestUsers.ADMIN);
        assertThat(response.status()).isEqualTo(HttpStatus.OK);
        var person = response.as(GetPersonResponse.class);

        var personRequest = updatePerson(person);

        response = fixture.send(HttpMethod.PUT, ROUTE + "/" + personRequest.id(), fixture.toJson(personRequest), TestUsers.ADMIN);
        assertThat(response.status()).isEqualTo(HttpStatus.OK);

        response = fixture.send(HttpMethod.GET, ROUTE + "/" + createPersonResponse.id(), null, TestUsers.ADMIN);
        assertThat(response.status()).isEqualTo(HttpStatus.OK);
        var updatedPerson = response.as(GetPersonResponse.class);

        var expectedPerson = jsonResources.readObject("Get_Person_Response.json", GetPersonResponse.class);
        assertEquivalentIgnoringIdentity(updatedPerson, expectedPerson);
        assertThat(updatedPerson.version()).isNotEqualTo(person.version());
        assertThat(updatedPerson.addresses()).allSatisfy(address -> assertThat(address.version()).isNotNull().isNotEqualTo(EMPTY_ID));
    }

    private static UpdatePersonRequest updatePerson(GetPersonResponse person) {
        return new UpdatePersonRequest(
                person.id(),
                "Joe", "El",
                LocalDate.of(2020, 5, 3),
                updateAddresses(person.addresses()),
                TestUsers.ADMIN.id(),
                CREATED_DATE,
                TestUsers.ADMIN.id(),
                MODIFIED_DATE,
                person.version());
    }

    private static List<UpdateAddressRequest> updateAddresses(List<GetAddressResponse> addresses) {
        var updated = new UpdateAddressRequest(
                addresses.get(1).id(),
                "Updated Street",
                "Updated Number",
                "Updated City",
                "Updated Postal Code",
                TestUsers.ADMIN.id(),
                CREATED_DATE,
                TestUsers.ADMIN.id(),
                MODIFIED_DATE,
                addresses.get(1).version());

        var added = new UpdateAddressRequest(
                EMPTY_ID,
                "Crypton Street",
                "Crypton Number",
                "Crypton City",
                "Crypton Postal Code",
                TestUsers.ADMIN.id(),
                CREATED_DATE,
                TestUsers.ADMIN.id(),
                MODIFIED_DATE,
                EMPTY_ID);

        return List.of(updated, added);
    }
}
