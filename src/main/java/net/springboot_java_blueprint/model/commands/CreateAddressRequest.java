package net.springboot_java_blueprint.model.commands;

import jakarta.validation.constraints.NotBlank;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateAddressRequest(
        @NotBlank String street,
        @NotBlank String number,
        @NotBlank String city,
        @NotBlank String postalCode,
        UUID createdId,
        OffsetDateTime createdDate) {
}
