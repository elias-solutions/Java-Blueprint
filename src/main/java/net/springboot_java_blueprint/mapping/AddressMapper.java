package net.springboot_java_blueprint.mapping;

import net.springboot_java_blueprint.entities.Address;
import net.springboot_java_blueprint.model.commands.CreateAddressRequest;
import net.springboot_java_blueprint.model.commands.UpdateAddressRequest;
import net.springboot_java_blueprint.model.queries.GetAddressResponse;

public final class AddressMapper {

    private AddressMapper() {
    }

    public static GetAddressResponse toGetAddressResponse(Address address) {
        return new GetAddressResponse(
                address.getId(),
                address.getStreet(),
                address.getNumber(),
                address.getCity(),
                address.getPostalCode(),
                address.getCreatedId(),
                address.getCreatedDate(),
                address.getModifiedId(),
                address.getModifiedDate(),
                address.getVersion());
    }

    public static Address toNewAddress(CreateAddressRequest source) {
        var address = new Address();
        address.setStreet(source.street());
        address.setNumber(source.number());
        address.setCity(source.city());
        address.setPostalCode(source.postalCode());
        address.setCreatedId(source.createdId());
        address.setCreatedDate(source.createdDate());
        return address;
    }

    public static Address toNewAddress(UpdateAddressRequest source) {
        var address = new Address();
        address.setStreet(source.street());
        address.setNumber(source.number());
        address.setCity(source.city());
        address.setPostalCode(source.postalCode());
        address.setCreatedId(source.createdId());
        address.setCreatedDate(source.createdDate());
        address.setModifiedId(source.modifiedId());
        address.setModifiedDate(source.modifiedDate());
        return address;
    }
}
