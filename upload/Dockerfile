FROM openjdk:8-jre-alpine

VOLUME /tmp

EXPOSE 8080

ADD build/libs/upload-1.0.0.jar upload-service.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/upload-service.jar"]
