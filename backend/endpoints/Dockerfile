### DOCKERFILE DEFAULT/BASE, por enquanto
## Autor: Lucas Mateus Braga de Souza
## Data: 20/06/2025
## - - - - - - - - - - - - - - - - - -

# Imagem base
FROM openjdk:23-jdk-slim AS build

WORKDIR /app

COPY pom.xml mvnw ./
COPY .mvn .mvn

RUN  chmod +x ./mvnw
RUN ./mvnw dependency:go-offline -B

COPY src ./src

RUN ./mvnw package -DskipTests

FROM openjdk:23-jdk-slim

WORKDIR /app

COPY --from=build /app/target/endpoints-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]