FROM maven:3.8.5-jdk-11-slim AS build
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package

FROM openjdk:11
ARG JAR_FILE=/app/target/challenge-0.0.1-SNAPSHOT.jar
COPY --from=build ${JAR_FILE} /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]