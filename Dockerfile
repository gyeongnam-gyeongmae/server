FROM openjdk:8

ARG JAR_FILE_PATH=build/libs/*.jar

COPY ${JAR_FILE_PATH} /

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=production", "/gyeongnam-gyeongmae-0.0.1-SNAPSHOT.jar"]