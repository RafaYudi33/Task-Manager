package br.com.rafaelyudi.todoList.IntegrationTests.Controller.Task;

import br.com.rafaelyudi.todoList.IntegrationTests.DTOs.TaskDTO;
import br.com.rafaelyudi.todoList.IntegrationTests.DTOs.UserDTO;
import br.com.rafaelyudi.todoList.IntegrationTests.Mocks.MockTaskRequests;
import br.com.rafaelyudi.todoList.IntegrationTests.Mocks.MockUserRequests;
import br.com.rafaelyudi.todoList.IntegrationTests.testcontainers.AbstractIntegrationTest;
import br.com.rafaelyudi.todoList.config.ObjectMapperConfig;
import br.com.rafaelyudi.todoList.config.TestConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.Comparator;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class TaskControllerWithJsonTest extends AbstractIntegrationTest {

    private static final String basePath = "/tasks/v1/";
    private static ObjectMapper objectMapper;
    private static RequestSpecification specification;
    private static UserDTO user;
    private static TaskDTO task;
    private static MockUserRequests mockUser;

    private static MockTaskRequests mockTask;

    private static final LocalDateTime startAt =  LocalDateTime.of(2030,1,30,10,0);
    private static final LocalDateTime endAt = LocalDateTime.of(2030,3,30,10,0);



    @BeforeAll
    public static void setUp(){
       objectMapper = ObjectMapperConfig.configureObjectMapper();
       mockUser = new MockUserRequests();
       mockTask = new MockTaskRequests();
       task = new TaskDTO(null, "Aula de Violão", "Aula", "Alta", startAt, endAt, null, null);
    }




    @DisplayName("Should create a task when everything is ok")
    @Test
    @Order(1)
    public void createTaskTestCase1() throws JsonProcessingException {
        user = mockUser.mockPostUser(objectMapper);
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
                .auth().preemptive().basic(user.getUsername(), user.getPassword())
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


    @DisplayName("Should return Invalid CORS Request when domain is not allowed to create a task")
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

    @DisplayName("Should find a task when everything is ok")
    @Test
    @Order(3)
    public void findTaskByIdTestCase1() throws JsonProcessingException {
        specification = new RequestSpecBuilder()
                .setPort(TestConfig.SERVER_PORT)
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ALLOWED_DOMAIN)
                .setContentType(TestConfig.MEDIA_TYPE_JSON)
                .setBasePath(basePath)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given()
                .spec(specification)
                .pathParam("idTask", task.getId())
                .auth().preemptive().basic("Rafael", "12345")
                .when()
                .get("{idTask}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        var taskFound = objectMapper.readValue(content, TaskDTO.class);

        assertNotNull(taskFound);
        assertNotNull(taskFound.getId());

        assertEquals("Aula", taskFound.getTitle());
        assertEquals("Aula de Violão", taskFound.getDescription());
        assertEquals(endAt, taskFound.getEndAt());
        assertEquals(user.getId(), taskFound.getIdUser());
        assertEquals("Alta", taskFound.getPriority());
        assertEquals(startAt, taskFound.getStartAt());
    }

    @DisplayName("Should return Invalid CORS Request when domain is not allowed to find a task")
    @Test
    @Order(4)
    public void findTaskByIdTestCase2(){
        specification = new RequestSpecBuilder()
                .setPort(TestConfig.SERVER_PORT)
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.NOT_ALLOWED_DOMAIN)
                .setContentType(TestConfig.MEDIA_TYPE_JSON)
                .setBasePath(basePath)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given()
                .spec(specification)
                .pathParam("idTask", task.getId())
                .auth().preemptive().basic("Rafael", "12345")
                .when()
                .get("{idTask}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", content);
    }


    @DisplayName("Should update when everything is ok")
    @Test
    @Order(5)
    public void updateTaskTestCase1() throws JsonProcessingException {
        TaskDTO contentToUpdate = new TaskDTO();
        contentToUpdate.setPriority("Baixa");
        contentToUpdate.setTitle("Título atualizado");

        specification = new RequestSpecBuilder()
                .setPort(TestConfig.SERVER_PORT)
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ALLOWED_DOMAIN)
                .setContentType(TestConfig.MEDIA_TYPE_JSON)
                .setBasePath(basePath)
                .setBody(contentToUpdate)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given()
                .spec(specification)
                .pathParam("idTask", task.getId())
                .auth().preemptive().basic("Rafael", "12345")
                .when()
                .put("{idTask}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        task = objectMapper.readValue(content, TaskDTO.class);

        assertNotNull(task);
        assertNotNull(task.getId());
        assertEquals("Título atualizado", task.getTitle());
        assertEquals("Aula de Violão", task.getDescription());
        assertEquals(endAt, task.getEndAt());
        assertEquals(user.getId(), task.getIdUser());
        assertEquals("Baixa", task.getPriority());
        assertEquals(startAt, task.getStartAt());

    }
    @DisplayName("Should return Invalid CORS request when domain is not allowed to update a task")
    @Test
    @Order(6)
    public void updateTaskTestCase2() {
        TaskDTO contentToUpdate = new TaskDTO();
        contentToUpdate.setPriority("Baixa");
        contentToUpdate.setTitle("Título atualizado");

        specification = new RequestSpecBuilder()
                .setPort(TestConfig.SERVER_PORT)
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.NOT_ALLOWED_DOMAIN)
                .setContentType(TestConfig.MEDIA_TYPE_JSON)
                .setBasePath(basePath)
                .setBody(contentToUpdate)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given()
                .spec(specification)
                .pathParam("idTask", task.getId())
                .auth().preemptive().basic("Rafael", "12345")
                .when()
                .put("{idTask}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", content);
    }




    @DisplayName("Should delete a task when everything is ok!")
    @Test
    @Order(7)
    public void deleteTaskTestCase1() {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ALLOWED_DOMAIN)
                .setPort(TestConfig.SERVER_PORT)
                .setBasePath(basePath)
                .setContentType(TestConfig.MEDIA_TYPE_JSON)
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        given()
                .spec(specification)
                .pathParam("taskId", task.getId())
                .auth().preemptive().basic("Rafael","12345")
                .when()
                .delete("{taskId}")
                .then()
                .statusCode(204);
    }

    @DisplayName("Should return Invalid CORS request when domain is not allowed to delete a task")
    @Test
    @Order(8)
    public void deleteTaskTestCase2() {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.NOT_ALLOWED_DOMAIN)
                .setPort(TestConfig.SERVER_PORT)
                .setBasePath(basePath)
                .setContentType(TestConfig.MEDIA_TYPE_JSON)
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given()
                .spec(specification)
                .pathParam("taskId", task.getId())
                .auth().preemptive().basic("Rafael","12345")
                .when()
                .delete("{taskId}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", content);
    }



    @DisplayName("Should get of all user´s tasks")
    @Test
    @Order(9)
    public void getTaskSpecificUserTestCase1() throws JsonProcessingException {

        var mockTasks = mockTask.mockPostListTasks(4,  user);

        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ALLOWED_DOMAIN)
                .setPort(TestConfig.SERVER_PORT)
                .setBasePath(basePath)
                .setContentType(TestConfig.MEDIA_TYPE_JSON)
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given()
                .spec(specification)
                .auth().preemptive().basic("Rafael","12345")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        List<TaskDTO> tasks =  objectMapper.readValue(content, new TypeReference<List<TaskDTO>>(){});

        tasks.sort(Comparator.comparing(TaskDTO::getCreatedAt));
        mockTasks.sort(Comparator.comparing(TaskDTO::getCreatedAt));
        for (int i = 0; i < tasks.size(); i++) {
            assertThat(tasks.get(i)).usingRecursiveComparison().isEqualTo(mockTasks.get(i));
        }
    }

    @DisplayName("Should return Invalid CORS request when domain is not allowed to get all of a user´s tasks")
    @Test
    @Order(10)
    public void getTaskSpecificUserTestCase2() {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.NOT_ALLOWED_DOMAIN)
                .setPort(TestConfig.SERVER_PORT)
                .setBasePath(basePath)
                .setContentType(TestConfig.MEDIA_TYPE_JSON)
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given()
                .spec(specification)
                .auth().preemptive().basic("Rafael","12345")
                .when()
                .get()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", content);
    }


}
