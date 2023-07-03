package br.com.eai.recruiting.livecode.controller.doc;

import br.com.eai.recruiting.livecode.request.AddressRequest;
import br.com.eai.recruiting.livecode.request.AddressesRequest;
import br.com.eai.recruiting.livecode.response.AddressResponse;
import br.com.eai.recruiting.livecode.response.AddressesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface AddressControllerDoc {
    @Operation(summary = "Create a new Address", method = "POST")
    @ApiResponses(value = {@ApiResponse(responseCode = "201",
            description = "Address created successfully"), @ApiResponse(responseCode = "400",
            description = "Invalid parameters"), @ApiResponse(responseCode = "500",
            description = "Internal Server Error")})
    ResponseEntity<AddressResponse> create(@RequestBody @Valid AddressRequest addressRequest);

    @Operation(summary = "Create address via batch operation", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Address created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    ResponseEntity<AddressesResponse> batchCreate(@RequestBody @Valid AddressesRequest addressesRequest);


    @Operation(summary = "Find addresses based on given zip-code", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    ResponseEntity<AddressesResponse> getAllByZipCode(
            @RequestParam
            @Pattern(regexp = "\\d{5}-\\d{3}", message = "Invalid zipCode (Valid example: 06420-100)")
            @Schema(defaultValue = "06420-100")
            String zipCode,
            @RequestParam(defaultValue = "0")
            Integer currentPage,
            @RequestParam(defaultValue = "10")
            Integer pageSize);
}
