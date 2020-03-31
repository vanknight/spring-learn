package com.learn.swagger2.config;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Cofnig {
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .produces(Sets.newHashSet("application/json"))
                .consumes(Sets.newHashSet("application/json"))
                .protocols(Sets.newHashSet("http","https"))
                .apiInfo(apiInfo())
                .forCodeGeneration(true)
                .select()
                //controller
                .apis(RequestHandlerSelectors.basePackage("com.learn.swagger2.controller"))
                .paths(PathSelectors.any())
                .build();

    }
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("test")
                .description("test-test")
//                .termsOfServiceUrl("")
                .version("v1")
                .build();
    }
}
