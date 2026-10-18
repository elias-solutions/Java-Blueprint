package net.springboot_java_blueprint.cqrs.queries;

import net.springboot_java_blueprint.cqrs.Query;
import net.springboot_java_blueprint.model.queries.GetPersonResponse;

import java.util.UUID;

public record GetPersonResponseByIdQuery(UUID personId) implements Query<GetPersonResponse> {
}
