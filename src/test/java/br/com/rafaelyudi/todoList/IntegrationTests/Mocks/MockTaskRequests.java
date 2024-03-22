package br.com.rafaelyudi.todoList.IntegrationTests.Mocks;

import br.com.rafaelyudi.todoList.IntegrationTests.DTOs.TaskDTO;
import br.com.rafaelyudi.todoList.IntegrationTests.DTOs.UserDTO;
import br.com.rafaelyudi.todoList.config.ObjectMapperConfig;
import br.com.rafaelyudi.todoList.config.TestConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MockTaskRequests {
    private static final LocalDateTime startAt =  LocalDateTime.of(2030,1,30,10,0);
    private static final LocalDateTime endAt = LocalDateTime.of(2030,3,30,10,0);
    private static final ObjectMapper objectMapper = ObjectMapperConfig.configureObjectMapper();

    public List<TaskDTO> mockPostListTasks(int taskNumberToMock, UserDTO userOwner) throws JsonProcessingException {
        List<TaskDTO> listOfTasksPersisted = new ArrayList<>();


        for (int i = 0; i < taskNumberToMock; i++) {
           TaskDTO task = new TaskDTO(null,
                   "Description"+i,
                   "Title"+i,
                   "Priority"+i,
                   startAt,
                   endAt,
                   null,
                   null
            );

            var specification = new RequestSpecBuilder()
                    .setPort(TestConfig.SERVER_PORT)
                    .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ALLOWED_DOMAIN)
                    .setContentType(TestConfig.MEDIA_TYPE_JSON)
                    .setBasePath(TestConfig.basePathTask)
                    .setBody(task)
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                    .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                    .build();

            var content = given()
                    .spec(specification)
                    .auth().preemptive().basic(userOwner.getUsername(), userOwner.getPassword())
                    .post()
                    .then()
                    .extract()
                    .body()
                    .asString();

            listOfTasksPersisted.add(i,objectMapper.readValue(content, TaskDTO.class));
            System.out.println("mocks:"+task.getTitle());
        }
        return listOfTasksPersisted;
    }


}
