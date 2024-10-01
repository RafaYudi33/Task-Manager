package br.com.rafaelyudi.todoList.IntegrationTests.testcontainers;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest  {

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext>{

        static PostgreSQLContainer<?> pgvector = new PostgreSQLContainer<>("pgvector/pgvector:pg16");


        private static void startContainers(){
            Startables.deepStart(Stream.of(pgvector)).join();
        }

        private static Map<String, Object> createConnection(){

            return Map.of(
                    "spring.datasource.url", pgvector.getJdbcUrl(),
                    "spring.datasource.username",pgvector.getUsername(),
                    "spring.datasource.password", pgvector.getPassword());
        }

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();
            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            MapPropertySource testcontainers = new MapPropertySource("testcontainers", createConnection());
            environment.getPropertySources().addFirst(testcontainers);
        }
    }
}
