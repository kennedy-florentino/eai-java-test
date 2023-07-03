package br.com.eai.recruiting.livecode.controller;

import br.com.eai.recruiting.livecode.controller.doc.AddressControllerDoc;
import br.com.eai.recruiting.livecode.request.AddressRequest;
import br.com.eai.recruiting.livecode.request.AddressesRequest;
import br.com.eai.recruiting.livecode.response.AddressResponse;
import br.com.eai.recruiting.livecode.response.AddressesResponse;
import br.com.eai.recruiting.livecode.service.AddressService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
@Validated
@RequiredArgsConstructor
public class AddressController implements AddressControllerDoc {

    private final AddressService addressService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressResponse> create(@RequestBody @Valid AddressRequest addressRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(addressService.createAddress(addressRequest));
    }

    @PostMapping(value = "/batch",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressesResponse> batchCreate(@RequestBody @Valid AddressesRequest addressesRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(addressService.batchCreateAddresses(addressesRequest));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressesResponse> getAllByZipCode(
            @RequestParam
            @Pattern(regexp = "\\d{5}-\\d{3}", message = "Invalid zipCode (Valid example: 06420-100)") String zipCode,
            @RequestParam(defaultValue = "0") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(addressService.getAllByZipCode(zipCode, currentPage, pageSize));
    }

}
