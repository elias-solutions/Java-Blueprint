package net.springboot_java_blueprint.cqrs.commands;

import net.springboot_java_blueprint.cqrs.CommandHandler;
import net.springboot_java_blueprint.dataaccess.AddressRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdateAddressCommandHandler implements CommandHandler<UpdateAddressCommand> {
    private final AddressRepository addressRepository;

    public UpdateAddressCommandHandler(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public void handle(UpdateAddressCommand command) {
        var request = command.request();
        var address = addressRepository.getOrThrow(request.addressId());
        addressRepository.verifyVersion(address, request.version());

        address.setCity(request.city());
        address.setNumber(request.number());
        address.setPostalCode(request.postalCode());
        address.setStreet(request.street());
        address.setModifiedId(request.modifiedId());
        address.setModifiedDate(request.modifiedDate());
    }
}
