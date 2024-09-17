package tourapi24.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SwaggerConfig {

    @Bean
    @Profile("prod")
    public OpenAPI prodOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("https://tourapi.seongmin.dev"))
                .info(new Info()
                        .title("Tour API")
                        .description("Tour API Documentation")
                        .version("v0.1.0"));
    }

    @Bean
    @Profile("local")
    public OpenAPI localOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("http://localhost:8080"))
                .info(new Info()
                        .title("Tour API")
                        .description("Tour API Documentation")
                        .version("v0.1.0"));
    }
}
