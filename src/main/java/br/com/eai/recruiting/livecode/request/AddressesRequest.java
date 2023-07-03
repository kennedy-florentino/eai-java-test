package br.com.eai.recruiting.livecode.request;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddressesRequest {
    private List<@Valid AddressRequest> addresses;
}
