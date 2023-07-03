package br.com.eai.recruiting.livecode.mapper;

import br.com.eai.recruiting.livecode.domain.Address;
import br.com.eai.recruiting.livecode.feign.rest.response.FetchAddressResponse;
import br.com.eai.recruiting.livecode.response.AddressResponse;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {

    @Mapping(source = "cep", target = "zipCode")
    @Mapping(source = "uf", target = "state")
    @Mapping(source = "localidade", target = "city")
    @Mapping(source = "bairro", target = "neighborhood")
    @Mapping(source = "logradouro", target = "street")
    @Mapping(expression = "java(number)", target = "number")
    Address toAddress(FetchAddressResponse fetchAddressResponse, @Context String number);

    AddressResponse toAddressResponse(Address address);
}
