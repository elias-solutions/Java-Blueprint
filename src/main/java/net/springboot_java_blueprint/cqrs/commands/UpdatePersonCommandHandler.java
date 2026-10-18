package net.springboot_java_blueprint.cqrs.commands;

import net.springboot_java_blueprint.cqrs.CommandHandler;
import net.springboot_java_blueprint.dataaccess.PersonRepository;
import net.springboot_java_blueprint.entities.Address;
import net.springboot_java_blueprint.entities.Person;
import net.springboot_java_blueprint.errorhandling.ProblemDetailsException;
import net.springboot_java_blueprint.mapping.AddressMapper;
import net.springboot_java_blueprint.model.commands.UpdateAddressRequest;
import net.springboot_java_blueprint.model.commands.UpdatePersonRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class UpdatePersonCommandHandler implements CommandHandler<UpdatePersonCommand> {
    private final PersonRepository personRepository;

    public UpdatePersonCommandHandler(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public void handle(UpdatePersonCommand command) {
        var request = command.request();
        var person = personRepository.findWithAddressesById(request.id())
                .orElseThrow(() -> new ProblemDetailsException(
                        HttpStatus.BAD_REQUEST, "No person found", "No person with id '%s' found.".formatted(request.id())));
        personRepository.verifyVersion(person, request.version());

        updatePersonFields(person, request);
        synchronizeAddresses(person, request.addresses());
    }

    private static void updatePersonFields(Person person, UpdatePersonRequest request) {
        person.setFirstName(request.firstName());
        person.setLastName(request.lastName());
        person.setBirthday(request.birthday());
        person.setCreatedId(request.createdId());
        person.setCreatedDate(request.createdDate());
        person.setModifiedId(request.modifiedId());
        person.setModifiedDate(request.modifiedDate());
    }

    private static void synchronizeAddresses(Person person, List<UpdateAddressRequest> requestedAddresses) {
        var existingRequests = requestedAddresses.stream()
                .filter(address -> !address.isNew())
                .collect(Collectors.toMap(UpdateAddressRequest::addressId, Function.identity()));

        person.getAddresses().removeIf(address -> !existingRequests.containsKey(address.getId()));
        person.getAddresses().forEach(address -> updateAddress(address, existingRequests));
        requestedAddresses.stream()
                .filter(UpdateAddressRequest::isNew)
                .map(AddressMapper::toNewAddress)
                .forEach(person.getAddresses()::add);
    }

    private static void updateAddress(Address address, Map<UUID, UpdateAddressRequest> requestsById) {
        var request = requestsById.get(address.getId());
        address.setCity(request.city());
        address.setNumber(request.number());
        address.setStreet(request.street());
        address.setPostalCode(request.postalCode());
        address.setCreatedId(request.createdId());
        address.setCreatedDate(request.createdDate());
        address.setModifiedId(request.modifiedId());
        address.setModifiedDate(request.modifiedDate());
    }
}
