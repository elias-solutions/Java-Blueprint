package net.springboot_java_blueprint.model.queries;

import net.springboot_java_blueprint.entities.Gender;
import net.springboot_java_blueprint.entities.PersonType;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record GetPersonResponse(
        UUID id,
        String firstName,
        String lastName,
        LocalDate birthday,
        Gender gender,
        String description,
        String email,
        String phone,
        PersonType type,
        List<GetAddressResponse> addresses,
        UUID createdId,
        OffsetDateTime createdDate,
        UUID modifiedId,
        OffsetDateTime modifiedDate,
        UUID version) {
}
