package br.com.rafaelyudi.todoList.IntegrationTests.Controller.User;

import br.com.rafaelyudi.todoList.IntegrationTests.DTOs.UserDTO;
import br.com.rafaelyudi.todoList.IntegrationTests.testcontainers.AbstractIntegrationTest;
import br.com.rafaelyudi.todoList.config.TestConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerWithJsonTest extends AbstractIntegrationTest{

    private static final String basePath = "/users/v1/";
    private static ObjectMapper objectMapper;
    private static RequestSpecification specification;
    private static UserDTO user;

    @BeforeAll
    public static void setUp(){
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.registerModule(new JavaTimeModule());
        user = new UserDTO(null, "Rafael", "Rafael Yudi","1234","rafamail", LocalDateTime.now());
    }

    @DisplayName("Should create user when everything is ok!")
    @Test
    @Order(1)
    public void createUserTestCase1() throws JsonProcessingException {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ALLOWED_DOMAIN)
                .setBasePath(basePath)
                .setBody(user)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .setContentType(TestConfig.MEDIA_TYPE_JSON)
                .setPort(TestConfig.SERVER_PORT)
                .build();

        var content = given().
                spec(specification)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();

        user = objectMapper.readValue(content, UserDTO.class);
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals("Rafael", user.getUsername());
        assertEquals("Rafael Yudi", user.getName());
    }


    @DisplayName("Should returns Invalid Cors Request when domain is not allowed")
    @Test
    @Order(2)
    public void createUserTestCase2(){
        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.NOT_ALLOWED_DOMAIN)
                .setBasePath(basePath)
                .setContentType(TestConfig.MEDIA_TYPE_JSON)
                .setPort(TestConfig.SERVER_PORT)
                .setBody(user)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given()
                .spec(specification)
                .when()
                .post()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", content);
    }


    @DisplayName("Should delete user when everything is ok")
    @Test
    @Order(3)
    public void deleteUserCase1(){

        var specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ALLOWED_DOMAIN)
                .setPort(TestConfig.SERVER_PORT)
                .setContentType(TestConfig.MEDIA_TYPE_JSON)
                .setBasePath(basePath)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();


        var responseStatusCode = given()
                .spec(specification)
                .auth().preemptive().basic("Rafael","1234")
                .pathParam("idUser", user.getId())
                        .when()
                                .delete("{idUser}")
                                        .then()
                                                .extract()
                                                        .statusCode();

        assertEquals(204, responseStatusCode);
    }
}
