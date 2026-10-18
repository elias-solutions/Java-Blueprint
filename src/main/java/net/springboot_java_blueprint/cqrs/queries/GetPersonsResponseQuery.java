package net.springboot_java_blueprint.cqrs.queries;

import net.springboot_java_blueprint.cqrs.Query;
import net.springboot_java_blueprint.model.queries.GetPersonResponse;

import java.util.List;

public record GetPersonsResponseQuery() implements Query<List<GetPersonResponse>> {
}
