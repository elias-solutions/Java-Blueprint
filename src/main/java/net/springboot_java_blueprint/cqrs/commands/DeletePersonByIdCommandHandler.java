package net.springboot_java_blueprint.cqrs.commands;

import net.springboot_java_blueprint.cqrs.CommandHandler;
import net.springboot_java_blueprint.dataaccess.PersonRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeletePersonByIdCommandHandler implements CommandHandler<DeletePersonByIdCommand> {
    private final PersonRepository personRepository;

    public DeletePersonByIdCommandHandler(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public void handle(DeletePersonByIdCommand command) {
        personRepository.removeById(command.personId());
    }
}
