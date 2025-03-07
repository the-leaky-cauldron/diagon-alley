package org.theleakycauldron.diagonalley.commons.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class DiagonAlleyOpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Sorting Hat API")
                        .version("v1")
                        .description("API for authentication and authorization of Diagon Alley")
                        .contact(new Contact()
                                .name("Vijaysurya Mandala")
                                .email("mandala.vijay.surya@gmail.com")
                                .url("https://www.github.com/mandalavijaysurya")
                        )
                );
    }
}