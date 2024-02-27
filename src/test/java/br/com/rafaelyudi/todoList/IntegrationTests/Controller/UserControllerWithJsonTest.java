package br.com.rafaelyudi.todoList.IntegrationTests.Controller;

import br.com.rafaelyudi.todoList.IntegrationTests.DTOs.UserDTO;
import br.com.rafaelyudi.todoList.IntegrationTests.testcontainers.AbstractIntegrationTest;
import br.com.rafaelyudi.todoList.config.TestConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerWithJsonTest extends AbstractIntegrationTest{

    private static ObjectMapper objectMapper;
    private static RequestSpecification specification;

    private static UserDTO user;

    @BeforeAll
    public static void setUp(){
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        user = new UserDTO(null, "Rafael", "Rafael Yudi","1234","rafamail", LocalDateTime.now());
    }




}
