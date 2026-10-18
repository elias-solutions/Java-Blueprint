package net.springboot_java_blueprint.environment;

import org.springframework.http.HttpStatus;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

public record TestResponse(HttpStatus status, String body, JsonMapper jsonMapper) {

    public <T> T as(Class<T> type) {
        return jsonMapper.readValue(body, type);
    }

    public <T> T as(TypeReference<T> type) {
        return jsonMapper.readValue(body, type);
    }
}
