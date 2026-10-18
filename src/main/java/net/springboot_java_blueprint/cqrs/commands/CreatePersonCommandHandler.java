package net.springboot_java_blueprint.cqrs.commands;

import net.springboot_java_blueprint.cqrs.ResultCommandHandler;
import net.springboot_java_blueprint.dataaccess.PersonRepository;
import net.springboot_java_blueprint.mapping.PersonMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreatePersonCommandHandler implements ResultCommandHandler<CreatePersonCommand, CreatePersonResponse> {
    private final PersonRepository personRepository;

    public CreatePersonCommandHandler(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public CreatePersonResponse handle(CreatePersonCommand command) {
        var person = personRepository.save(PersonMapper.toNewPerson(command.request()));
        return new CreatePersonResponse(person.getId());
    }
}
