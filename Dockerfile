# Etapa de build
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app
COPY pom.xml .

# Instalação do Maven
RUN apt-get update && apt-get install -y maven

# Baixar dependências sem rodar os testes
RUN mvn dependency:go-offline

# Copiar o código-fonte e compilar
COPY src ./src
RUN mvn package -DskipTests

# Etapa de runtime
FROM eclipse-temurin:17-jdk-jammy AS runtime
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
