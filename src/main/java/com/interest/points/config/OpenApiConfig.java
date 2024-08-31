package com.interest.points.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info().title("Points Of Interest API")
                        .version("v1")
                        .description("This API allows you to manage and query geographically distributed points of " +
                                "interest, providing features for creating, searching by proximity, and filtering by " +
                                "specific categories.")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Renato Farias")
                                .url("https://www.linkedin.com/in/renatofari4s")
                        )
                );
    }
}
