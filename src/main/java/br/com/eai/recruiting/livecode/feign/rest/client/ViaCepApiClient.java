package br.com.eai.recruiting.livecode.feign.rest.client;

import br.com.eai.recruiting.livecode.feign.rest.response.ViaCepApiResponse;
import br.com.eai.recruiting.livecode.service.CepService;
import feign.Param;
import feign.RequestLine;

public interface ViaCepApiClient extends CepService {

    @RequestLine("GET /{zipCode}/json")
    ViaCepApiResponse fetchAddressByZipCode(@Param String zipCode);
}
