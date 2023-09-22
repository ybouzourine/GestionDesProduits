package com.bouzourine.gestiondesproduits.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "BOUZOURINE",
                        email = "bouzourine.youcef@hotmail.com"
                ),
                description = "OpenApi documentation for Product rest api",
                title = "Product rest Api",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080/"
                )
        }
)
public class OpenApiConfig {

}
