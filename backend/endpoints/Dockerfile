### DOCKERFILE DEFAULT/BASE
## Autor: Lucas Mateus Braga de Souza
## Data: 20/06/2025
## - - - - - - - - - - - - - - - - - -

# Imagem base
FROM openjdk:21-jdk-slim AS build

WORKDIR /app

COPY pom.xml .
COPY .settings . 
COPY mvnw .
COPY .mvn/ .mvn/

RUN ./mvnw dependency:go-offline -B

COPY src/ ./src/

RUN ./mvnw package -DskipTests

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/target/endpoints-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]