package net.springboot_java_blueprint.cqrs.commands;

import net.springboot_java_blueprint.cqrs.Command;
import net.springboot_java_blueprint.model.commands.UpdateAddressRequest;

public record UpdateAddressCommand(UpdateAddressRequest request) implements Command {
}
