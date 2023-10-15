# Estágio de construção
FROM openjdk:18
WORKDIR /app
COPY . .
RUN apt-get update && apt-get install -y maven
RUN mvn clean install

# Estágio de execução
FROM openjdk:18-jdk-slim
EXPOSE 8080
COPY --from=0 /app/target/todolist-1.0.0.jar /app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
