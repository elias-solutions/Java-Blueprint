package net.springboot_java_blueprint.cqrs.commands;

import net.springboot_java_blueprint.cqrs.ResultCommand;
import net.springboot_java_blueprint.model.commands.CreatePersonRequest;

public record CreatePersonCommand(CreatePersonRequest request) implements ResultCommand<CreatePersonResponse> {
}
