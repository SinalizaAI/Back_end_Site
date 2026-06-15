# Stage 1 - Compila o projeto com Maven
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copia o pom.xml e baixa dependências (cache eficiente)
COPY pom.xml .
RUN apk add --no-cache maven
RUN mvn dependency:go-offline -B

# Copia o código-fonte e compila
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2 - Imagem final só com o JAR
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]