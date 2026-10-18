package net.springboot_java_blueprint.model.commands;

import jakarta.validation.constraints.NotBlank;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UpdateAddressRequest(
        UUID addressId,
        @NotBlank String street,
        @NotBlank String number,
        @NotBlank String city,
        @NotBlank String postalCode,
        UUID createdId,
        OffsetDateTime createdDate,
        UUID modifiedId,
        OffsetDateTime modifiedDate,
        UUID version) {

    private static final UUID EMPTY_ID = new UUID(0, 0);

    public boolean isNew() {
        return addressId == null || EMPTY_ID.equals(addressId);
    }
}
