package br.com.rafaelyudi.todoList.IntegrationTests.Mocks;

import br.com.rafaelyudi.todoList.IntegrationTests.DTOs.UserDTO;
import br.com.rafaelyudi.todoList.User.LoginResponseDTO;
import br.com.rafaelyudi.todoList.User.UserCredentialsDTO;
import br.com.rafaelyudi.todoList.config.TestConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

import static io.restassured.RestAssured.given;
public class MockUserRequest {

    public MockUserRequest() {
    }

    public UserDTO mockPostUser(ObjectMapper objectMapper) throws JsonProcessingException {
        var user = new UserDTO(null, "Rafael", "Rafael Yudi","12345","rafamail", null);
        var specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ALLOWED_DOMAIN)
                .setBasePath(TestConfig.basePathUser)
                .setBody(user)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .setContentType(TestConfig.MEDIA_TYPE_JSON)
                .setPort(TestConfig.SERVER_PORT)
                .build();

        var content = given().
                spec(specification)
                .post("/register")
                .then()
                .extract()
                .body()
                .asString();

        var userDTOWithoutPassCrypt = objectMapper.readValue(content, UserDTO.class);
        userDTOWithoutPassCrypt.setPassword(user.getPassword());

        return userDTOWithoutPassCrypt;
    }

    public String mockUserLogin(UserDTO user){

        UserCredentialsDTO credentials = new UserCredentialsDTO(user.getUsername(),user.getPassword());

        return given()
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

    }
}
