# openjdk 17 이미지를 상속
FROM openjdk:17

# JAR 파일 위치
ARG APP_JAR_FILE_PATH=backend/module-api-app/build/libs/*.jar

ARG JASYPT_PASSWORD=default

ENV JASYPT_PASSWORD_ENV=$JASYPT_PASSWORD

# jar파일을 app.jar로 복사
COPY ${APP_JAR_FILE_PATH} /

# 컨테이너가 최종 실행할 명령어 정의: jar 파일 실행
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=production", "-Djasypt.encryptor.password=$JASYPT_PASSWORD_ENV", "/module-api-app.jar"]
