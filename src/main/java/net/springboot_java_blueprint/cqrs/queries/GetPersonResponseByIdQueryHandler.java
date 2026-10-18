package net.springboot_java_blueprint.cqrs.queries;

import net.springboot_java_blueprint.cqrs.QueryHandler;
import net.springboot_java_blueprint.dataaccess.PersonRepository;
import net.springboot_java_blueprint.errorhandling.ProblemDetailsException;
import net.springboot_java_blueprint.mapping.PersonMapper;
import net.springboot_java_blueprint.model.queries.GetPersonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GetPersonResponseByIdQueryHandler implements QueryHandler<GetPersonResponseByIdQuery, GetPersonResponse> {
    private final PersonRepository personRepository;

    public GetPersonResponseByIdQueryHandler(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public GetPersonResponse handle(GetPersonResponseByIdQuery query) {
        return personRepository.findWithAddressesById(query.personId())
                .map(PersonMapper::toGetPersonResponse)
                .orElseThrow(() -> new ProblemDetailsException(
                        HttpStatus.BAD_REQUEST, "No person found", "No person with id '%s' found.".formatted(query.personId())));
    }
}
