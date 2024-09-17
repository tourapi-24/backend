# Build stage
FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace/app

COPY gradle gradle
COPY build.gradle settings.gradle gradlew ./
COPY src src

RUN ./gradlew build -x test

# Production stage
FROM eclipse-temurin:21-jre

VOLUME /tmp
COPY --from=build /workspace/app/build/libs/*.jar app.jar
COPY src/main/resources/private_key.pem /private_key.pem
COPY src/main/resources/public_key.pem /public_key.pem

ENTRYPOINT ["java","-jar","/app.jar"]
