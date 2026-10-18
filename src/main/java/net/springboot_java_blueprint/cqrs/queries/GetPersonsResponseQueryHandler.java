package net.springboot_java_blueprint.cqrs.queries;

import net.springboot_java_blueprint.cqrs.QueryHandler;
import net.springboot_java_blueprint.dataaccess.PersonRepository;
import net.springboot_java_blueprint.mapping.PersonMapper;
import net.springboot_java_blueprint.model.queries.GetPersonResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class GetPersonsResponseQueryHandler implements QueryHandler<GetPersonsResponseQuery, List<GetPersonResponse>> {
    private final PersonRepository personRepository;

    public GetPersonsResponseQueryHandler(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetPersonResponse> handle(GetPersonsResponseQuery query) {
        return personRepository.findAll().stream()
                .map(PersonMapper::toGetPersonResponse)
                .toList();
    }
}
