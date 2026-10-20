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
class PostWriterOkTest {
    private static final String ROUTE = "/api/v1/persons";

    @Autowired
    private Fixture fixture;

    @Test
    void personsController_Post_Writer_Ok() throws Exception {
        var content = fixture.jsonResources(PostWriterOkTest.class).readJson("Post_Person_Request.json");
        var response = fixture.send(HttpMethod.POST, ROUTE, content, TestUsers.WRITER);
        assertThat(response.status()).isEqualTo(HttpStatus.OK);
    }
}
