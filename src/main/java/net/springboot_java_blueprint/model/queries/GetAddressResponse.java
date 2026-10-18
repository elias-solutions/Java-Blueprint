package net.springboot_java_blueprint.model.queries;

import java.time.OffsetDateTime;
import java.util.UUID;

public record GetAddressResponse(
        UUID id,
        String street,
        String number,
        String city,
        String postalCode,
        UUID createdId,
        OffsetDateTime createdDate,
        UUID modifiedId,
        OffsetDateTime modifiedDate,
        UUID version) {
}
