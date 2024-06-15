package br.com.rafaelyudi.todoList.Config.Swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI().info(new Info()
                .title("Task Manager")
                .description("An api to manage tasks")
                .version("Version 1")
                .contact(new Contact().email("yudirafael33@gmail.com")))
                .components(new Components().addSecuritySchemes("Bearer Authentication",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                        ))
               ;
    }


    @Bean
    public GroupedOpenApi customGroupedOpenApi(){
        return GroupedOpenApi.builder().group("default").pathsToMatch("/**/v1/**").build();
    }
}
