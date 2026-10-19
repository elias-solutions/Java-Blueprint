package net.springboot_java_blueprint.mapping;

import net.springboot_java_blueprint.entities.Person;
import net.springboot_java_blueprint.model.commands.CreatePersonRequest;
import net.springboot_java_blueprint.model.queries.GetPersonResponse;

public final class PersonMapper {

    private PersonMapper() {
    }

    public static GetPersonResponse toGetPersonResponse(Person person) {
        return new GetPersonResponse(
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getBirthday(),
                person.getGender(),
                person.getDescription(),
                person.getEmail(),
                person.getPhone(),
                person.getType(),
                person.getAddresses().stream().map(AddressMapper::toGetAddressResponse).toList(),
                person.getCreatedId(),
                person.getCreatedDate(),
                person.getModifiedId(),
                person.getModifiedDate(),
                person.getVersion());
    }

    public static Person toNewPerson(CreatePersonRequest source) {
        var person = new Person();
        person.setFirstName(source.firstName());
        person.setLastName(source.lastName());
        person.setBirthday(source.birthday());
        person.setGender(source.gender());
        person.setDescription(source.description());
        person.setEmail(source.email());
        person.setPhone(source.phone());
        person.setType(source.type());
        person.setCreatedId(source.createdId());
        person.setCreatedDate(source.createdDate());
        source.addresses().stream()
                .map(AddressMapper::toNewAddress)
                .forEach(person.getAddresses()::add);
        return person;
    }
}
