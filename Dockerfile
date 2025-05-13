# Etapa de construção
FROM --platform=linux/arm64 mcr.microsoft.com/openjdk/jdk:17-mariner AS build
WORKDIR /app
COPY pom.xml .

RUN apt-get update && apt-get install -y maven

RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package -DskipTests

# Etapa de runtime
FROM --platform=linux/arm64 mcr.microsoft.com/openjdk/jdk:17-mariner AS runtime
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
