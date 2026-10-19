package net.springboot_java_blueprint.environment;

import net.springboot_java_blueprint.dataaccess.AddressRepository;
import net.springboot_java_blueprint.dataaccess.PersonRepository;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Fixture {
    private final TestTokenIssuer tokenIssuer;
    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;
    private final JsonMapper jsonMapper;
    private final Environment environment;
    private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();

    public Fixture(
            TestTokenIssuer tokenIssuer,
            PersonRepository personRepository,
            AddressRepository addressRepository,
            JsonMapper jsonMapper,
            Environment environment) {
        this.tokenIssuer = tokenIssuer;
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
        this.jsonMapper = jsonMapper;
        this.environment = environment;
    }

    /** Sends the request with a valid access token for {@code user}; {@code null} sends it anonymously. */
    public TestResponse send(HttpMethod method, String route, String jsonContent, TestUser user)
            throws IOException, InterruptedException {
        return sendWithToken(method, route, jsonContent, user == null ? null : tokenIssuer.issue(user));
    }

    public TestResponse sendWithToken(HttpMethod method, String route, String jsonContent, String accessToken)
            throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder(URI.create(baseUrl() + route))
                .header("Accept", MediaType.APPLICATION_JSON_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .method(method.name(), jsonContent == null
                        ? HttpRequest.BodyPublishers.noBody()
                        : HttpRequest.BodyPublishers.ofString(jsonContent));
        if (accessToken != null) {
            request.header("Authorization", "Bearer " + accessToken);
        }

        var response = httpClient.send(request.build(), HttpResponse.BodyHandlers.ofString());
        return new TestResponse(HttpStatus.valueOf(response.statusCode()), response.body(), jsonMapper);
    }

    public TestTokenIssuer tokens() {
        return tokenIssuer;
    }

    public String toJson(Object value) {
        return jsonMapper.writeValueAsString(value);
    }

    public JsonResourceProvider jsonResources(Class<?> testClass) {
        return new JsonResourceProvider(testClass, jsonMapper);
    }

    public void resetDatabase() {
        personRepository.deleteAll();
        addressRepository.deleteAll();
    }

    private String baseUrl() {
        return "http://localhost:" + environment.getRequiredProperty("local.server.port");
    }
}
