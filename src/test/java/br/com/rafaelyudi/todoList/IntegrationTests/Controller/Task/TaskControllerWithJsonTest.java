package br.com.rafaelyudi.todoList.IntegrationTests.Controller.Task;

import br.com.rafaelyudi.todoList.IntegrationTests.DTOs.TaskDTO;
import br.com.rafaelyudi.todoList.IntegrationTests.DTOs.UserDTO;
import br.com.rafaelyudi.todoList.IntegrationTests.testcontainers.AbstractIntegrationTest;
import br.com.rafaelyudi.todoList.config.TestConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class TaskControllerWithJsonTest extends AbstractIntegrationTest {

    private static final String basePath = "/tasks/v1/";
    private static ObjectMapper objectMapper;
    private static RequestSpecification specification;
    private static UserDTO user;
    private static TaskDTO task;

    private static final LocalDateTime startAt =  LocalDateTime.of(2030,1,30,10,0);
    private static final LocalDateTime endAt = LocalDateTime.of(2030,3,30,10,0);



    @BeforeAll
    public static void setUp(){
       objectMapper = new ObjectMapper();
       objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
       objectMapper.registerModule(new JavaTimeModule());
       user = new UserDTO(null, "Rafael", "Rafael Yudi","12345","rafamail", null);
       task = new TaskDTO(null, "Aula de Violão", "Aula", "Alta", startAt, endAt, null, null);


    }


    public void createAnUserToTestTasks() throws JsonProcessingException {

        var specificationUser =  new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ALLOWED_DOMAIN)
                .setBasePath("/users/v1/")
                .setBody(user)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .setContentType(TestConfig.MEDIA_TYPE_JSON)
                .setPort(TestConfig.SERVER_PORT)
                .build();

       var content = given()
                .spec(specificationUser)
               .when()
               .post()
               .then()
               .extract()
               .body()
               .asString();

       user = objectMapper.readValue(content , UserDTO.class);
    }

    @DisplayName("Should create a task when everything is ok")
    @Test
    @Order(1)
    public void createTaskTestCase1() throws JsonProcessingException {

        createAnUserToTestTasks();

        specification = new RequestSpecBuilder()
                .setPort(TestConfig.SERVER_PORT)
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ALLOWED_DOMAIN)
                .setContentType(TestConfig.MEDIA_TYPE_JSON)
                .setBasePath(basePath)
                .setBody(task)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given()
                .spec(specification)
                .auth().preemptive().basic("Rafael", "12345")
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();

        task = objectMapper.readValue(content, TaskDTO.class);

        assertNotNull(task);
        assertNotNull(task.getId());

        assertEquals("Aula", task.getTitle());
        assertEquals("Aula de Violão", task.getDescription());
        assertEquals(endAt, task.getEndAt());
        assertEquals(user.getId(), task.getIdUser());
        assertEquals("Alta", task.getPriority());
        assertEquals(startAt, task.getStartAt());
    }


    @DisplayName("Should return Invalid CORS Request when domain is not allowed")
    @Test
    @Order(2)
    public void createTaskTestCase2(){
        specification = new RequestSpecBuilder()
                .setPort(TestConfig.SERVER_PORT)
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.NOT_ALLOWED_DOMAIN)
                .setContentType(TestConfig.MEDIA_TYPE_JSON)
                .setBasePath(basePath)
                .setBody(task)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given()
                .spec(specification)
                .auth().preemptive().basic("Rafael", "12345")
                .when()
                .post()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", content);

    }




}
