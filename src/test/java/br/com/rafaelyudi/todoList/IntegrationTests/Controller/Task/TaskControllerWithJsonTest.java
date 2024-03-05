package br.com.rafaelyudi.todoList.IntegrationTests.Controller.Task;

import br.com.rafaelyudi.todoList.IntegrationTests.DTOs.TaskDTO;
import br.com.rafaelyudi.todoList.IntegrationTests.DTOs.UserDTO;
import br.com.rafaelyudi.todoList.IntegrationTests.testcontainers.AbstractIntegrationTest;
import br.com.rafaelyudi.todoList.config.TestConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class TaskControllerWithJsonTest extends AbstractIntegrationTest {

    private static String basePath = "/tasks/v1";
    private static ObjectMapper objectMapper;
    private static RequestSpecification specification;
    private static UserDTO user;
    private static TaskDTO task;


    @BeforeAll
    public static void setUp(){
       objectMapper = new ObjectMapper();
       objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
       user = new UserDTO(null, "Rafael", "Rafael Yudi","1234","rafamail", LocalDateTime.now());
       task = new TaskDTO(null, "Aula de Violão", "Aula", "Alta",
               LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2),null,LocalDateTime.now());

       new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ALLOWED_DOMAIN)
                .setBasePath(basePath)
                .setBody(user)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .setContentType(TestConfig.MEDIA_TYPE_JSON)
                .setPort(TestConfig.SERVER_PORT)
                .build();

        given()
                .spec(specification)
                .post();

    }

    @Test
    @Order(1)
    public void createTaskTestCase1(){

    }





}
