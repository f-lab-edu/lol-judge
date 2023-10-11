# openjdk 17 이미지를 상속
FROM openjdk:17

# JAR 파일 위치
ARG APP_JAR_FILE_PATH=backend/module-api-app/build/libs/*.jar

# jar파일을 app.jar로 복사
COPY ${APP_JAR_FILE_PATH} /

# 컨테이너가 최종 실행할 명령어 정의: jar 파일 실행
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=production", "/module-api-app.jar"]
