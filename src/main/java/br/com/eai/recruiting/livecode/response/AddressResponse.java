package br.com.eai.recruiting.livecode.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponse {
    @Schema(defaultValue = "06420-100")
    private String zipCode;

    @Schema(defaultValue = "SP")
    private String state;

    @Schema(defaultValue = "Barueri")
    private String city;

    @Schema(defaultValue = "Vila Nova")
    private String neighborhood;

    @Schema(defaultValue = "Rua Pintassilgo")
    private String street;

    @Schema(defaultValue = "123")
    private String number;
}
