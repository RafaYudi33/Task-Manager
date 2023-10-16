# Estágio de construção
FROM ubuntu:20.04 AS build

RUN apt-get update && apt-get install -y openjdk-17-jdk

WORKDIR /app

# Copie o conteúdo do diretório local para o diretório /app no contêiner
COPY . /app

RUN apt-get install -y maven
RUN mvn clean install

# Estágio de execução
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copie o arquivo JAR da etapa de construção
COPY --from=build /app/target/todolist-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
