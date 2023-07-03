package br.com.eai.recruiting.livecode.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressesResponse {
    private List<AddressResponse> addresses;
}
