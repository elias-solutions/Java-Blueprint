package net.springboot_java_blueprint.cqrs.queries;

import net.springboot_java_blueprint.cqrs.QueryHandler;
import net.springboot_java_blueprint.dataaccess.AddressRepository;
import net.springboot_java_blueprint.mapping.AddressMapper;
import net.springboot_java_blueprint.model.queries.GetAddressResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GetAddressResponseByIdQueryHandler implements QueryHandler<GetAddressResponseByIdQuery, GetAddressResponse> {
    private final AddressRepository addressRepository;

    public GetAddressResponseByIdQueryHandler(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public GetAddressResponse handle(GetAddressResponseByIdQuery query) {
        return AddressMapper.toGetAddressResponse(addressRepository.getOrThrow(query.id()));
    }
}
