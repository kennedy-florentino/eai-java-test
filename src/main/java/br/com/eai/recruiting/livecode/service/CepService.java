package br.com.eai.recruiting.livecode.service;

import br.com.eai.recruiting.livecode.feign.rest.response.FetchAddressResponse;
import org.springframework.stereotype.Service;

@Service
public interface CepService {
    FetchAddressResponse fetchAddressByZipCode(String zipCode);
}
