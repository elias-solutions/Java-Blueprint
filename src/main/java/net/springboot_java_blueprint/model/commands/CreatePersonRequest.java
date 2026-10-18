package net.springboot_java_blueprint.model.commands;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record CreatePersonRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotNull LocalDate birthday,
        UUID createdId,
        OffsetDateTime createdDate,
        @NotNull List<@Valid CreateAddressRequest> addresses) {
}
