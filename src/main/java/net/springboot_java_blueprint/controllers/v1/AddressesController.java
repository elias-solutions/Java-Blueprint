package net.springboot_java_blueprint.controllers.v1;

import jakarta.validation.Valid;
import net.springboot_java_blueprint.cqrs.CommandHandler;
import net.springboot_java_blueprint.cqrs.QueryHandler;
import net.springboot_java_blueprint.cqrs.commands.UpdateAddressCommand;
import net.springboot_java_blueprint.cqrs.queries.GetAddressResponseByIdQuery;
import net.springboot_java_blueprint.errorhandling.ProblemDetailsException;
import net.springboot_java_blueprint.model.commands.UpdateAddressRequest;
import net.springboot_java_blueprint.model.queries.GetAddressResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/addresses")
public class AddressesController {
    private final CommandHandler<UpdateAddressCommand> updateAddressCommandHandler;
    private final QueryHandler<GetAddressResponseByIdQuery, GetAddressResponse> getAddressByIdQueryHandler;

    public AddressesController(
            CommandHandler<UpdateAddressCommand> updateAddressCommandHandler,
            QueryHandler<GetAddressResponseByIdQuery, GetAddressResponse> getAddressByIdQueryHandler) {
        this.updateAddressCommandHandler = updateAddressCommandHandler;
        this.getAddressByIdQueryHandler = getAddressByIdQueryHandler;
    }

    @PutMapping("/{id}")
    public GetAddressResponse updateAddress(@PathVariable UUID id, @Valid @RequestBody UpdateAddressRequest request) {
        if (!id.equals(request.addressId())) {
            throw new ProblemDetailsException(HttpStatus.BAD_REQUEST, "Id mismatch",
                    "Provided AddressId '%s' and AddressId '%s' are not equal.".formatted(id, request.addressId()));
        }

        updateAddressCommandHandler.handle(new UpdateAddressCommand(request));
        return getAddressByIdQueryHandler.handle(new GetAddressResponseByIdQuery(request.addressId()));
    }
}
