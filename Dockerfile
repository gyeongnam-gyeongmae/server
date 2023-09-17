# 배포 관련 파일입니다. 담당자와 상의 없이 수정 금지
FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/gyeongnam-gyeongmae-0.0.1.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar","-Dspring.profile.active=dev"]
EXPOSE 12000
