package net.springboot_java_blueprint.model.commands;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record UpdatePersonRequest(
        @NotNull UUID id,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotNull LocalDate birthday,
        @NotNull List<@Valid UpdateAddressRequest> addresses,
        UUID createdId,
        OffsetDateTime createdDate,
        UUID modifiedId,
        OffsetDateTime modifiedDate,
        UUID version) {
}
