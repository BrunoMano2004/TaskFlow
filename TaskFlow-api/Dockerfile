# 1) Build com Maven + Temurin 17 (multi‐arch)
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 2) Runtime leve em Alpine para ARM32v7 com Temurin 17 JRE
FROM arm32v7/eclipse-temurin:17-jre-alpine AS runtime
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
