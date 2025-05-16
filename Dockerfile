# syntax=docker/dockerfile:1.0.0

# Etapa de build
FROM --platform=linux/arm64/v8 arm64v8/maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# Etapa de runtime
FROM --platform=linux/arm64/v8 arm64v8/eclipse-temurin:17-jre-jammy AS runtime
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
