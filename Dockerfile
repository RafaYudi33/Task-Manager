FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/task-manager-1.0.0.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar"]