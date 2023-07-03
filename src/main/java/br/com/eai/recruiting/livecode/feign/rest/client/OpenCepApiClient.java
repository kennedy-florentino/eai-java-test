package br.com.eai.recruiting.livecode.feign.rest.client;

import br.com.eai.recruiting.livecode.feign.rest.response.OpenCepApiResponse;
import br.com.eai.recruiting.livecode.service.CepService;
import feign.Param;
import feign.RequestLine;

public interface OpenCepApiClient extends CepService {

    @RequestLine("GET /{zipCode}")
    OpenCepApiResponse fetchAddressByZipCode(@Param String zipCode);
}
