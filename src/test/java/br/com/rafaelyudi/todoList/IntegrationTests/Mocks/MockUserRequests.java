package br.com.rafaelyudi.todoList.IntegrationTests.Mocks;

import br.com.rafaelyudi.todoList.IntegrationTests.DTOs.UserDTO;
import br.com.rafaelyudi.todoList.config.TestConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

import static io.restassured.RestAssured.given;
public class MockUserRequests {

    public MockUserRequests() {
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
                .post()
                .then()
                .extract()
                .body()
                .asString();

        var userDTOWithoutPassCrypt = objectMapper.readValue(content, UserDTO.class);
        userDTOWithoutPassCrypt.setPassword(user.getPassword());

        return userDTOWithoutPassCrypt;
    }
}
