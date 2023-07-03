package br.com.eai.recruiting.livecode.feign.rest.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OpenCepApiResponse extends FetchAddressResponse {
    private String ibge;
}
