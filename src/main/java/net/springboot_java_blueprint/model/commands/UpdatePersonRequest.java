package net.springboot_java_blueprint.model.commands;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import net.springboot_java_blueprint.entities.Gender;
import net.springboot_java_blueprint.entities.Person;
import net.springboot_java_blueprint.entities.PersonType;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record UpdatePersonRequest(
        @NotNull UUID id,
        @NotBlank String firstName,
        @NotBlank String lastName,
        LocalDate birthday,
        Gender gender,
        @Size(max = Person.DESCRIPTION_MAX_LENGTH) String description,
        @Email @Size(max = Person.EMAIL_MAX_LENGTH) String email,
        @Size(max = Person.PHONE_MAX_LENGTH) String phone,
        PersonType type,
        @NotNull List<@Valid UpdateAddressRequest> addresses,
        UUID createdId,
        OffsetDateTime createdDate,
        UUID modifiedId,
        OffsetDateTime modifiedDate,
        UUID version) {
}
