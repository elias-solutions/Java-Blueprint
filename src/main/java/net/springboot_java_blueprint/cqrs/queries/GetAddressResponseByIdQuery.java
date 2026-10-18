package net.springboot_java_blueprint.cqrs.queries;

import net.springboot_java_blueprint.cqrs.Query;
import net.springboot_java_blueprint.model.queries.GetAddressResponse;

import java.util.UUID;

public record GetAddressResponseByIdQuery(UUID id) implements Query<GetAddressResponse> {
}
