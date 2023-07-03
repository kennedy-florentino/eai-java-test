package br.com.eai.recruiting.livecode.feign.rest.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ViaCepApiResponse extends FetchAddressResponse{
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;
}
