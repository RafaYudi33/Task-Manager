package br.com.rafaelyudi.todoList.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI().info(new Info()
                .title("To do list")
                .description("An api to manage tasks")
                .version("Version 1")
                .contact(new Contact().email("yudirafael33@gmail.com")));

    };


    @Bean
    public GroupedOpenApi customGroupedOpenApi(){
        return GroupedOpenApi.builder().group("Tasks").pathsToMatch("/**/v1/**").build();
    }
}
