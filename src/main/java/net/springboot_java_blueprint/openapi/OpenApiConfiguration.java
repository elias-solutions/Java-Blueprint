package net.springboot_java_blueprint.openapi;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    private static final String TITLE = "Spring Boot Web API";

    @Bean
    public GroupedOpenApi v1Api() {
        return versionGroup("v1", "1.0");
    }

    @Bean
    public GroupedOpenApi v2Api() {
        return versionGroup("v2", "2.0");
    }

    private static GroupedOpenApi versionGroup(String group, String version) {
        return GroupedOpenApi.builder()
                .group(group)
                .pathsToMatch("/api/" + group + "/**")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title(TITLE).version(version)))
                .build();
    }
}
