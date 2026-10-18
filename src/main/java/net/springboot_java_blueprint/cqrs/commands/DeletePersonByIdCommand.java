package net.springboot_java_blueprint.cqrs.commands;

import net.springboot_java_blueprint.cqrs.Command;

import java.util.UUID;

public record DeletePersonByIdCommand(UUID personId) implements Command {
}
