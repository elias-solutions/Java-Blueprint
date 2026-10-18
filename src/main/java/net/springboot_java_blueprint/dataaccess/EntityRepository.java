package net.springboot_java_blueprint.dataaccess;

import net.springboot_java_blueprint.entities.EntityBase;
import net.springboot_java_blueprint.errorhandling.ProblemDetailsException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@NoRepositoryBean
public interface EntityRepository<T extends EntityBase> extends JpaRepository<T, UUID> {

    default T getOrThrow(UUID id) {
        return findById(id).orElseThrow(() -> new ProblemDetailsException(
                HttpStatus.BAD_REQUEST, "No entity found", "No entity with id '%s' found.".formatted(id)));
    }

    default void verifyVersion(T entity, UUID expectedVersion) {
        if (!entity.getVersion().equals(expectedVersion)) {
            throw new ProblemDetailsException(
                    HttpStatus.BAD_REQUEST, "Entity version conflict", "Entity has been updated through other user.");
        }
    }

    default void removeById(UUID id) {
        var entity = findById(id).orElseThrow(() -> new ProblemDetailsException(
                HttpStatus.BAD_REQUEST, "Entity not found", "No entity found with id '%s'".formatted(id)));
        delete(entity);
    }
}
