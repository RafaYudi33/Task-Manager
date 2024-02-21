package br.com.rafaelyudi.todoList.Swagger;

import br.com.rafaelyudi.todoList.config.TestConfig;
import static org.junit.jupiter.api.Assertions.*;

import br.com.rafaelyudi.todoList.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {


    @Test
    public void swaggerIntegrationTest(){
        var content = given()
                .basePath("swagger-ui/index.html")
                .port(TestConfig.SERVER_PORT)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        assertTrue(content.contains("Swagger UI"));
    }

}
