FROM openjdk:8-jdk-alpine

ARG JAR_FILE_PATH=target/**.jar

COPY --chown=appuser:appuser ${JAR_FILE_PATH} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=production", "/app.jar"]