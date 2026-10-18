package net.springboot_java_blueprint.cqrs;

public interface QueryHandler<Q extends Query<R>, R> {
    R handle(Q query);
}
