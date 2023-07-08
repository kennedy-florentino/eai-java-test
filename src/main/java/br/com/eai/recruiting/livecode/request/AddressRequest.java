package br.com.eai.recruiting.livecode.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class AddressRequest {

    @Positive
    @Schema(defaultValue = "1")
    private Integer version;

    @EqualsAndHashCode.Include
    @NotEmpty
    @Pattern(regexp = "\\d{8}", message = "Invalid zipCode (Valid example: 06420100)")
    @Schema(defaultValue = "06420100")
    private String zipCode;

    @EqualsAndHashCode.Include
    @NotEmpty
    @Positive
    @Schema(defaultValue = "123")
    private String number;
}
