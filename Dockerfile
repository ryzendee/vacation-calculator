FROM maven:3.9-eclipse-temurin-11-alpine AS build
WORKDIR /app
COPY src /app/src
COPY pom.xml /app/
RUN mvn clean package -DskipTests

FROM eclipse-temurin:11-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 7090
ENTRYPOINT ["java", "-jar", "app.jar"]
