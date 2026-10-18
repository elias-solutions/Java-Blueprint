package net.springboot_java_blueprint.cqrs.commands;

import net.springboot_java_blueprint.cqrs.Command;
import net.springboot_java_blueprint.model.commands.UpdatePersonRequest;

public record UpdatePersonCommand(UpdatePersonRequest request) implements Command {
}
