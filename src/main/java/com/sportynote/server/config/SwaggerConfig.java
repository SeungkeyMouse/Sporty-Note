package com.sportynote.server.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SW Maestro 13th-SportyNote 프로젝트")
                .description("인증티켓 팀의 RESTful API 프로젝트 내용입니다. ")
                .build();
    }
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Arrays.asList(apiKey()));
    }
    private ApiKey apiKey() {
        return new ApiKey("accessToken", "Authorization", "header");
    }

}