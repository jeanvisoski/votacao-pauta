package com.api.scheduleVoting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.api.scheduleVoting.controller"))
                .paths(PathSelectors.regex("/api/v1.*")).build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API Serviço Votação")
                .description("API de serviços de votação orientado pela abertura de uma pauta para ser votada" +
                        " durante um determinado período, onde somente pode haver um voto por associado.")
                .version("1.0.0")
                .contact(
                        new Contact("Jean Augusto Visoski", null, "jean.visoski@compasso.com.br")
                )
                .build();
    }
}