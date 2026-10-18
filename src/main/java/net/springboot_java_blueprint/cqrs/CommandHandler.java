package net.springboot_java_blueprint.cqrs;

public interface CommandHandler<C extends Command> {
    void handle(C command);
}
