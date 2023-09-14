FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/gyeongnam-gyeongmae-0.0.1.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar","-Dspring.profile.active=dev"]
EXPOSE 8081
