package br.com.eai.recruiting.livecode.feign.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FetchAddressResponse {
    protected String cep;
    protected String logradouro;
    protected String complemento;
    protected String bairro;
    protected String localidade;
    protected String uf;
}
