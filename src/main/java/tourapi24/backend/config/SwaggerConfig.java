package tourapi24.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SwaggerConfig {

    private OpenAPI baseOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")))
                .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"))
                .info(new Info()
                        .title("Tour API")
                        .description("Tour API Documentation")
                        .version("v0.1.0"));
    }

    @Bean
    @Profile("prod")
    public OpenAPI prodOpenAPI() {
        return baseOpenAPI()
                .addServersItem(new Server().url("https://tourapi.seongmin.dev"));
    }

    @Bean
    @Profile("local")
    public OpenAPI localOpenAPI() {
        return baseOpenAPI()
                .addServersItem(new Server().url("http://localhost:8080"));
    }
}
