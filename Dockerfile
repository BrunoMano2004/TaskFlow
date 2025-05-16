# Etapa de build
FROM arm64v8/maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package -DskipTests

# Etapa de runtime
FROM arm64v8/eclipse-temurin:17.0.12_7-jre-jammy AS runtime
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
