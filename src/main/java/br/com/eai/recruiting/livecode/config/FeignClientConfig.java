package br.com.eai.recruiting.livecode.config;

import br.com.eai.recruiting.livecode.feign.rest.client.OpenCepApiClient;
import br.com.eai.recruiting.livecode.feign.rest.client.ViaCepApiClient;
import br.com.eai.recruiting.livecode.service.CepService;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class FeignClientConfig {
    @Value("${spring.cloud.openfeign.client.config.viacep.url}")
    private String viacepUrl;

    @Value("${spring.cloud.openfeign.client.config.opencep.url}")
    private String opencepUrl;

    @Bean
    protected Feign.Builder feignBuilder() {
        return new Feign.Builder().decoder(new JacksonDecoder());
    }

    @Bean
    public CepService viaCepApiClient() {
        return feignBuilder().target(ViaCepApiClient.class, viacepUrl);
    }

    @Bean
    public CepService openCepApiClient() {
        return feignBuilder().target(OpenCepApiClient.class, opencepUrl);
    }
}