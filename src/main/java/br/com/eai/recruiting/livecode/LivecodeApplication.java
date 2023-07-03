package br.com.eai.recruiting.livecode;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(info = @Info(
        title = "Address API",
        version = "1",
        description = "API improved after live coding interview",
        contact = @Contact(name = "Kennedy Florentino", email = "kennedyf2k@gmail.com"))
)
public class LivecodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(LivecodeApplication.class, args);
    }

}
