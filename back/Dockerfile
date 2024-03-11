# 빌드 스테이지
FROM openjdk:21-slim as build
WORKDIR /workspace/app

# 애플리케이션의 소스 코드와 gradle 빌드 파일을 이미지로 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY src src

# 실행 가능한 JAR 파일 빌드
RUN ./gradlew build -x test

# 런타임 스테이지
FROM openjdk:21-slim
WORKDIR /app

# 빌드 스테이지에서 생성된 JAR 파일을 런타임 이미지로 복사
COPY --from=build /workspace/app/build/libs/*.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java","-jar","app.jar"]
