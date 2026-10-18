package net.springboot_java_blueprint.controllers.v1;

import jakarta.validation.Valid;
import net.springboot_java_blueprint.cqrs.CommandHandler;
import net.springboot_java_blueprint.cqrs.QueryHandler;
import net.springboot_java_blueprint.cqrs.ResultCommandHandler;
import net.springboot_java_blueprint.cqrs.commands.CreatePersonCommand;
import net.springboot_java_blueprint.cqrs.commands.CreatePersonResponse;
import net.springboot_java_blueprint.cqrs.commands.DeletePersonByIdCommand;
import net.springboot_java_blueprint.cqrs.commands.UpdatePersonCommand;
import net.springboot_java_blueprint.cqrs.queries.GetPersonResponseByIdQuery;
import net.springboot_java_blueprint.cqrs.queries.GetPersonsResponseQuery;
import net.springboot_java_blueprint.errorhandling.ProblemDetailsException;
import net.springboot_java_blueprint.model.commands.CreatePersonRequest;
import net.springboot_java_blueprint.model.commands.UpdatePersonRequest;
import net.springboot_java_blueprint.model.queries.GetPersonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController("personsControllerV1")
@RequestMapping("/api/v1/persons")
public class PersonsController {
    private final QueryHandler<GetPersonsResponseQuery, List<GetPersonResponse>> getPersonsQueryHandler;
    private final QueryHandler<GetPersonResponseByIdQuery, GetPersonResponse> getPersonByIdQueryHandler;
    private final ResultCommandHandler<CreatePersonCommand, CreatePersonResponse> createPersonCommandHandler;
    private final CommandHandler<UpdatePersonCommand> updatePersonCommandHandler;
    private final CommandHandler<DeletePersonByIdCommand> deletePersonByIdCommandHandler;

    public PersonsController(
            QueryHandler<GetPersonsResponseQuery, List<GetPersonResponse>> getPersonsQueryHandler,
            QueryHandler<GetPersonResponseByIdQuery, GetPersonResponse> getPersonByIdQueryHandler,
            ResultCommandHandler<CreatePersonCommand, CreatePersonResponse> createPersonCommandHandler,
            CommandHandler<UpdatePersonCommand> updatePersonCommandHandler,
            CommandHandler<DeletePersonByIdCommand> deletePersonByIdCommandHandler) {
        this.getPersonsQueryHandler = getPersonsQueryHandler;
        this.getPersonByIdQueryHandler = getPersonByIdQueryHandler;
        this.createPersonCommandHandler = createPersonCommandHandler;
        this.updatePersonCommandHandler = updatePersonCommandHandler;
        this.deletePersonByIdCommandHandler = deletePersonByIdCommandHandler;
    }

    @GetMapping
    public List<GetPersonResponse> getPersons() {
        return getPersonsQueryHandler.handle(new GetPersonsResponseQuery());
    }

    @GetMapping("/{id}")
    public GetPersonResponse getPersonById(@PathVariable UUID id) {
        return getPersonByIdQueryHandler.handle(new GetPersonResponseByIdQuery(id));
    }

    @PostMapping
    public CreatePersonResponse createPerson(@Valid @RequestBody CreatePersonRequest request) {
        return createPersonCommandHandler.handle(new CreatePersonCommand(request));
    }

    @PutMapping("/{id}")
    public void updatePerson(@PathVariable UUID id, @Valid @RequestBody UpdatePersonRequest request) {
        if (!id.equals(request.id())) {
            throw new ProblemDetailsException(HttpStatus.BAD_REQUEST, "Id mismatch",
                    "Provided Id '%s' and Id '%s' are not equal.".formatted(id, request.id()));
        }

        updatePersonCommandHandler.handle(new UpdatePersonCommand(request));
    }

    @DeleteMapping("/{id}")
    public void deletePersonById(@PathVariable UUID id) {
        deletePersonByIdCommandHandler.handle(new DeletePersonByIdCommand(id));
    }
}
