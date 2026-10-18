package net.springboot_java_blueprint.cqrs;

public interface ResultCommandHandler<C extends ResultCommand<R>, R> {
    R handle(C command);
}
