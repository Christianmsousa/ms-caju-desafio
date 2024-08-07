package com.caju_desafio.ms_caju_desafio.entrypoint.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
@NoArgsConstructor
@ConfigurationProperties(prefix = "caju.info.build")
public class OpenApiConfig {

    private String apiName;
    private String description;
    private String version;


    @Bean
    public OpenAPI springOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title(apiName)
                        .description(description)
                        .version(version)
                        .license(new License().name("Christian Marçal").url("https://github.com/Christianmsousa")));
    }
}
