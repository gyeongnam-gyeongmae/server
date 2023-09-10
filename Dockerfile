FROM openjdk:8-jdk-alpine
CMD ls
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
CMD cat app.jar
ENTRYPOINT ["java","-jar","/app.jar","-Dspring.profile.active=dev"]
EXPOSE 8080