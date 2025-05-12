# Etapa de construção
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app
COPY pom.xml .

RUN apt-get update && apt-get install -y maven

RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package -DskipTests

# Etapa de runtime
FROM eclipse-temurin:17-jre-jammy AS runtime
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
