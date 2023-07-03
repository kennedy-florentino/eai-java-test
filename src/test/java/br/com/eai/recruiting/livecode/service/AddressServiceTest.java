package br.com.eai.recruiting.livecode.service;

import br.com.eai.recruiting.livecode.config.FeignClientConfig;
import br.com.eai.recruiting.livecode.domain.Address;
import br.com.eai.recruiting.livecode.exception.DuplicatedAddressException;
import br.com.eai.recruiting.livecode.exception.InvalidVersionException;
import br.com.eai.recruiting.livecode.feign.rest.client.OpenCepApiClient;
import br.com.eai.recruiting.livecode.feign.rest.client.ViaCepApiClient;
import br.com.eai.recruiting.livecode.feign.rest.response.FetchAddressResponse;
import br.com.eai.recruiting.livecode.mapper.AddressMapper;
import br.com.eai.recruiting.livecode.repository.AddressRepository;
import br.com.eai.recruiting.livecode.request.AddressRequest;
import br.com.eai.recruiting.livecode.request.AddressesRequest;
import br.com.eai.recruiting.livecode.response.AddressResponse;
import br.com.eai.recruiting.livecode.response.AddressesResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@SpringBootTest
public class AddressServiceTest {

    @Autowired
    private AddressService addressService;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private FeignClientConfig feignClientConfig;

    @MockBean
    private ViaCepApiClient viaCepApiClient;

    @MockBean
    private OpenCepApiClient openCepApiClient;

    @Mock
    private CepService cepService;

    @Spy
    private AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);

    @Test
    @DisplayName("Given duplicated AddressRequest when batchCreateAddresses should throw DuplicatedAddressException")
    void batchCreateAddressesWithDuplicatedAddressTest() {
        final AddressRequest addressRequest1 = AddressRequest.builder()
                                                             .zipCode("06420100")
                                                             .number("1")
                                                             .version(1)
                                                             .build();

        final AddressRequest addressRequest2 = AddressRequest.builder()
                                                             .zipCode("06420100")
                                                             .number("1")
                                                             .version(2)
                                                             .build();

        Assertions.assertThrows(
                DuplicatedAddressException.class,
                () -> addressService.batchCreateAddresses(new AddressesRequest(List.of(
                        addressRequest1,
                        addressRequest2
                )))
        );
    }

    @Test
    @DisplayName("Given invalid version when createAddress should throw InvalidVersionException")
    void createAddressWithInvalidVersion() {
        final AddressRequest addressRequest = AddressRequest.builder()
                                                            .zipCode("06420100")
                                                            .number("1")
                                                            .version(3)
                                                            .build();

        Assertions.assertThrows(
                InvalidVersionException.class,
                () -> addressService.createAddress(addressRequest)
        );
    }

    @Test
    @DisplayName("Given AddressRequest should create new Address and return AddressResponse")
    void createAddressTest() {
        final AddressRequest addressRequest = AddressRequest.builder()
                                                            .zipCode("06420100")
                                                            .number("100")
                                                            .version(1)
                                                            .build();

        final FetchAddressResponse fetchAddressResponse = new FetchAddressResponse();
        fetchAddressResponse.setCep(addressRequest.getZipCode());
        fetchAddressResponse.setUf("STATE");
        fetchAddressResponse.setLocalidade("CITY");
        fetchAddressResponse.setBairro("NEIGHBORHOOD");
        fetchAddressResponse.setLogradouro("STREET");

        final Address address = Address.builder()
                                       .zipCode(addressRequest.getZipCode())
                                       .state(fetchAddressResponse.getUf())
                                       .city(fetchAddressResponse.getLocalidade())
                                       .neighborhood(fetchAddressResponse.getBairro())
                                       .street(fetchAddressResponse.getLogradouro())
                                       .number(addressRequest.getNumber())
                                       .build();


        final AddressResponse expectedResponse = addressMapper.toAddressResponse(address);

        Mockito.when(cepService.fetchAddressByZipCode(Mockito.any(String.class))).thenReturn(fetchAddressResponse);
        Mockito.when(feignClientConfig.viaCepApiClient()).thenReturn(cepService);
        Mockito.when(feignClientConfig.openCepApiClient()).thenReturn(cepService);

        final AddressResponse actualResponse = addressService.createAddress(addressRequest);

        Mockito.verify(cepService, Mockito.times(1)).fetchAddressByZipCode(addressRequest.getZipCode());
        Mockito.verify(addressRepository, Mockito.times(1)).save(Mockito.any());
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Given AddressesRequest should create all and return AdressesResponse")
    void batchCreateAddressesTest() {
        final AddressRequest addressRequest1 = AddressRequest.builder()
                                                             .zipCode("06420100")
                                                             .number("1")
                                                             .version(1)
                                                             .build();

        final AddressRequest addressRequest2 = AddressRequest.builder()
                                                             .zipCode("06420100")
                                                             .number("2")
                                                             .version(2)
                                                             .build();

        final FetchAddressResponse fetchAddressResponse1 = new FetchAddressResponse(
                addressRequest1.getZipCode(),
                "STREET",
                "COMPLEMENT",
                "NEIGHBORHOOD",
                "CITY",
                "STATE"
        );

        final FetchAddressResponse fetchAddressResponse2 = new FetchAddressResponse(
                addressRequest2.getZipCode(),
                "STREET",
                "COMPLEMENT",
                "NEIGHBORHOOD",
                "CITY",
                "STATE"
        );

        final Address address1 = addressMapper.toAddress(fetchAddressResponse1, addressRequest1.getNumber());
        final Address address2 = addressMapper.toAddress(fetchAddressResponse2, addressRequest2.getNumber());

        final AddressesResponse expectedResponse =
                new AddressesResponse(List.of(
                        addressMapper.toAddressResponse(address1),
                        addressMapper.toAddressResponse(address2)
                ));

        Mockito.when(cepService.fetchAddressByZipCode(Mockito.any(String.class))).thenReturn(fetchAddressResponse1);
        Mockito.when(feignClientConfig.viaCepApiClient()).thenReturn(cepService);
        Mockito.when(feignClientConfig.openCepApiClient()).thenReturn(cepService);
        Mockito.when(addressMapper.toAddress(fetchAddressResponse1, addressRequest1.getNumber())).thenReturn(address1);
        Mockito.when(addressMapper.toAddress(fetchAddressResponse2, addressRequest2.getNumber())).thenReturn(address2);
        Mockito.when(addressRepository.save(address1)).thenReturn(address1);
        Mockito.when(addressRepository.save(address2)).thenReturn(address2);


        final AddressesResponse actualResponse = addressService.batchCreateAddresses(new AddressesRequest(List.of(
                addressRequest1,
                addressRequest2
        )));

        Mockito.verify(cepService, Mockito.times(2)).fetchAddressByZipCode(Mockito.any());
        Mockito.verify(addressRepository, Mockito.times(2)).save(Mockito.any());
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Given zipCode, currentPage, and pageSize, should get all addresses and return AddressesResponse")
    void getAllByZipCodeTest() {
        final String zipCode = "06420100";
        final int currentPage = 0;
        final int pageSize = 10;

        final Pageable pageable = PageRequest.of(currentPage, pageSize);

        final List<Address> addressList = List.of(
                Address.builder().build(),
                Address.builder().build()
        );

        final AddressesResponse expectedResponse = new AddressesResponse(
                List.of(
                        AddressResponse.builder().build(),
                        AddressResponse.builder().build()
                )
        );

        Mockito.when(addressRepository.findAllByZipCode(zipCode, pageable)).thenReturn(addressList);

        final AddressesResponse actualResponse = addressService.getAllByZipCode(zipCode, currentPage, pageSize);

        Mockito.verify(addressRepository, Mockito.times(1)).findAllByZipCode(zipCode, pageable);
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

}