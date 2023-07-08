package br.com.eai.recruiting.livecode.service;

import br.com.eai.recruiting.livecode.config.FeignClientConfig;
import br.com.eai.recruiting.livecode.domain.Address;
import br.com.eai.recruiting.livecode.exception.DuplicatedAddressException;
import br.com.eai.recruiting.livecode.exception.InvalidVersionException;
import br.com.eai.recruiting.livecode.feign.rest.response.FetchAddressResponse;
import br.com.eai.recruiting.livecode.mapper.AddressMapper;
import br.com.eai.recruiting.livecode.repository.AddressRepository;
import br.com.eai.recruiting.livecode.request.AddressRequest;
import br.com.eai.recruiting.livecode.request.AddressesRequest;
import br.com.eai.recruiting.livecode.response.AddressResponse;
import br.com.eai.recruiting.livecode.response.AddressesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final FeignClientConfig feignClientConfig;
    private CepService cepService;

    @Transactional
    public AddressResponse createAddress(AddressRequest addressRequest) {
        setCepService(addressRequest.getVersion());
        final FetchAddressResponse fetchAddressResponse = cepService.fetchAddressByZipCode(addressRequest.getZipCode());
        final Address address = addressMapper.toAddress(
                fetchAddressResponse,
                addressRequest.getNumber()
        );
        addressRepository.save(address);
        return addressMapper.toAddressResponse(address);
    }

    @Transactional
    public AddressesResponse batchCreateAddresses(AddressesRequest addressesRequest) {
        final List<AddressRequest> addressRequestList = addressesRequest.getAddresses();

        if (addressRequestList.stream().distinct().count() < addressRequestList.size())
            throw new DuplicatedAddressException("Duplicated addresses in batch create operation");

        final List<AddressResponse> addressResponseList = addressRequestList
                .stream()
                .map(this::createAddress)
                .toList();

        return new AddressesResponse(addressResponseList);
    }

    public AddressesResponse getAllByZipCode(String zipCode, Integer currentPage, Integer pageSize) {
        final Pageable pageable = PageRequest.of(currentPage, pageSize);
        final List<Address> addressList = addressRepository.findAllByZipCode(zipCode, pageable);
        return new AddressesResponse(addressList
                .stream()
                .map(addressMapper::toAddressResponse)
                .collect(Collectors.toList()));
    }

    private void setCepService(Integer version) {
        switch (version) {
            case 1 -> cepService = feignClientConfig.viaCepApiClient();
            case 2 -> cepService = feignClientConfig.openCepApiClient();
            default -> throw new InvalidVersionException("Invalid version");
        }
    }
}
