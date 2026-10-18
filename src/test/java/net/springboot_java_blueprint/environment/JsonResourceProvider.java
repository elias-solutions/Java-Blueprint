package net.springboot_java_blueprint.environment;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

/**
 * Reads JSON test resources that live in the same package as the test class.
 */
public class JsonResourceProvider {
    private final Class<?> testClass;
    private final JsonMapper jsonMapper;

    public JsonResourceProvider(Class<?> testClass, JsonMapper jsonMapper) {
        this.testClass = testClass;
        this.jsonMapper = jsonMapper;
    }

    public String readJson(String fileName) {
        try (InputStream stream = testClass.getResourceAsStream(fileName)) {
            if (stream == null) {
                throw new IllegalArgumentException(
                        "No resource file '%s' found next to '%s'.".formatted(fileName, testClass.getName()));
            }
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    public <T> T readObject(String fileName, Class<T> type) {
        return jsonMapper.readValue(readJson(fileName), type);
    }

    public <T> T readObject(String fileName, TypeReference<T> type) {
        return jsonMapper.readValue(readJson(fileName), type);
    }
}
