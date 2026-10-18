package net.springboot_java_blueprint.dataaccess;

import net.springboot_java_blueprint.entities.Person;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends EntityRepository<Person> {

    @EntityGraph(attributePaths = "addresses")
    Optional<Person> findWithAddressesById(UUID id);

    @Override
    @EntityGraph(attributePaths = "addresses")
    List<Person> findAll();
}
