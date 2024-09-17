FROM eclipse-temurin:21-jre

VOLUME /tmp

COPY build/libs/*.jar app.jar
COPY src/main/resources/private_key.pem /private_key.pem
COPY src/main/resources/public_key.pem /public_key.pem

ENTRYPOINT ["java","-jar","/app.jar"]
