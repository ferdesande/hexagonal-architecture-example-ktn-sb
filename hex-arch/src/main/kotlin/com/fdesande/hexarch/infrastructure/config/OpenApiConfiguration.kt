package com.fdesande.hexarch.infrastructure.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfiguration {
    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Hexagonal architecture API")
                    .version("1.0")
                    .description("API documentation for the sample hexagonal architecture")
                    .contact(
                        Contact()
                            .name("Fernando de Sande")
                            .email("fdesande@gmail.com")
                    )
            )
    }
}
