package br.com.rafaelyudi.todoList.IntegrationTests.Controller.User;

import br.com.rafaelyudi.todoList.Config.SecurityConfig;
import br.com.rafaelyudi.todoList.IntegrationTests.DTOs.UserDTO;
import br.com.rafaelyudi.todoList.IntegrationTests.testcontainers.AbstractIntegrationTest;
import br.com.rafaelyudi.todoList.User.LoginResponseDTO;
import br.com.rafaelyudi.todoList.User.UserCredentialsDTO;
import br.com.rafaelyudi.todoList.config.ObjectMapperConfig;
import br.com.rafaelyudi.todoList.config.TestConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class UserControllerWithJsonTest extends AbstractIntegrationTest{

    private static ObjectMapper objectMapper;
    private static RequestSpecification specification;
    private static UserDTO user;

    private static String tokenUser;

    @BeforeAll
    public static void setUp(){
        objectMapper = ObjectMapperConfig.configureObjectMapper();
        user = new UserDTO(null, "Rafael", "Rafael Yudi","1234","rafamail", null);
    }

    @DisplayName("Should create user when everything is ok!")
    @Test
    @Order(1)
    public void createUserTestCase1() throws JsonProcessingException {

        var content = given().
                basePath(TestConfig.basePathUser)
                .port(TestConfig.SERVER_PORT)
                .header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ALLOWED_DOMAIN)
                .contentType(TestConfig.MEDIA_TYPE_JSON)
                .filter(new RequestLoggingFilter(LogDetail.ALL))
                .filter(new ResponseLoggingFilter(LogDetail.ALL))
                .body(user)
                .when()
                .post("/register")
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

        var content = given().port(TestConfig.SERVER_PORT)
                .basePath(TestConfig.basePathUser)
                .filter(new RequestLoggingFilter(LogDetail.ALL))
                .filter(new ResponseLoggingFilter(LogDetail.ALL))
                .header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.NOT_ALLOWED_DOMAIN)
                .body(user)
                .contentType(TestConfig.MEDIA_TYPE_JSON)
                .when()
                .post("/register")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", content);
    }

    @DisplayName("Should login a user when everything is ok")
    @Test
    @Order(3)
    public void loginUserTestCase1(){
        UserCredentialsDTO credentials = new UserCredentialsDTO("Rafael","1234");

        tokenUser = given()
                .basePath(TestConfig.basePathUser)
                .port(TestConfig.SERVER_PORT)
                .contentType(TestConfig.MEDIA_TYPE_JSON)
                .filter(new RequestLoggingFilter(LogDetail.ALL))
                .filter(new ResponseLoggingFilter(LogDetail.ALL))
                .header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ALLOWED_DOMAIN)
                .body(credentials)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(LoginResponseDTO.class).token();

        specification = new RequestSpecBuilder()
                .setBasePath(TestConfig.basePathUser)
                .addHeader(TestConfig.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenUser)
                .setPort(TestConfig.SERVER_PORT)
                .setContentType(TestConfig.MEDIA_TYPE_JSON)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }


    @DisplayName("Should returns Invalid Cors Request when domain is not allowed")
    @Test
    @Order(4)
    public void deleteUserCase1(){

        var content = given()
                .spec(specification)
                .header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.NOT_ALLOWED_DOMAIN)
                .when()
                .delete("/")
                .then()
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", content);
    }

    @DisplayName("Should delete user when everything is ok")
    @Test
    @Order(5)
    public void deleteUserCase2(){

        var responseStatusCode = given()
                .spec(specification)
                .header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ALLOWED_DOMAIN)
                        .when()
                                .delete("/")
                                        .then()
                                                .extract()
                                                        .statusCode();

        assertEquals(204, responseStatusCode);
    }
}
