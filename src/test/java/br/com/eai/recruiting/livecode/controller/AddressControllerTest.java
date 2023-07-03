package br.com.eai.recruiting.livecode.controller;

import br.com.eai.recruiting.livecode.domain.Address;
import br.com.eai.recruiting.livecode.mapper.AddressMapper;
import br.com.eai.recruiting.livecode.request.AddressRequest;
import br.com.eai.recruiting.livecode.request.AddressesRequest;
import br.com.eai.recruiting.livecode.response.AddressResponse;
import br.com.eai.recruiting.livecode.response.AddressesResponse;
import br.com.eai.recruiting.livecode.service.AddressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest
public class AddressControllerTest {

    final static String ADDRESS_API = "/api/address";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @Spy
    private AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);

    @Test
    @DisplayName("Given AddressRequest should return status code CREATED and AddressResponse as body")
    void createTest() throws Exception {
        final AddressRequest addressRequest = AddressRequest.builder()
                                                            .zipCode("06420100")
                                                            .number("100")
                                                            .version(1)
                                                            .build();

        final Address address = Address.builder()
                                       .id(1L)
                                       .zipCode(addressRequest.getZipCode())
                                       .state("STATE")
                                       .city("CITY")
                                       .neighborhood("NEIGHBORHOOD")
                                       .street("STREET")
                                       .number(addressRequest.getNumber())
                                       .build();

        final AddressResponse addressResponse = addressMapper.toAddressResponse(address);

        Mockito
                .when(addressService.createAddress(Mockito.any(AddressRequest.class)))
                .thenReturn(addressResponse);

        final MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(ADDRESS_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addressRequest));

        mockMvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(addressResponse)));
    }

    @Test
    @DisplayName("Given AddressesRequest should return status code CREATED and AddressesResponse as body")
    void batchCreateTest() throws Exception {
        final AddressesRequest addressesRequest = new AddressesRequest(List.of(
                AddressRequest.builder()
                              .zipCode("06420100")
                              .number("100")
                              .version(1)
                              .build(),
                AddressRequest.builder()
                              .zipCode("06420100")
                              .number("200")
                              .version(2)
                              .build()
        ));

        final List<Address> addressList = addressesRequest
                .getAddresses()
                .stream()
                .map(addressRequest -> Address.builder()
                                              .id(1L)
                                              .zipCode(addressRequest.getZipCode())
                                              .state("STATE")
                                              .city("CITY")
                                              .neighborhood("NEIGHBORHOOD")
                                              .street("STREET")
                                              .number(addressRequest.getNumber())
                                              .build()
                )
                .toList();

        final AddressesResponse addressesResponse = new AddressesResponse(
                addressList.stream()
                           .map(address -> addressMapper.toAddressResponse(address))
                           .toList());

        Mockito
                .when(addressService.batchCreateAddresses(Mockito.any(AddressesRequest.class)))
                .thenReturn(addressesResponse);

        final MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(ADDRESS_API + "/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addressesRequest));

        mockMvc.perform(request)
               .andExpect(MockMvcResultMatchers.status().isCreated())
               .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(addressesResponse)));
    }

    @Test
    @DisplayName("Given zipCode, currentPage and pageSize should return status code OK and AddressesResponse as body")
    void getAllByZipCodeTest() throws Exception {
        final String zipCode = "06420-100";
        final Integer currentPage = 0;
        final Integer pageSize = 10;

        final AddressesResponse addressesResponse = new AddressesResponse(List.of(
                AddressResponse.builder()
                               .zipCode(zipCode)
                               .number("100")
                               .build(),
                AddressResponse.builder()
                               .zipCode(zipCode)
                               .number("200")
                               .build()
        ));

        Mockito.when(addressService.getAllByZipCode(zipCode, currentPage, pageSize)).thenReturn(addressesResponse);

        mockMvc.perform(MockMvcRequestBuilders.get(ADDRESS_API)
                                              .param("zipCode", zipCode)
                                              .param("currentPage", currentPage.toString())
                                              .param("pageSize", pageSize.toString()))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(addressesResponse)));
    }
}
