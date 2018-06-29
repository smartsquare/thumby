FROM openjdk:8-jre-alpine

VOLUME /tmp

EXPOSE 8080

ADD build/libs/image-upload-service-1.0.0-SNAPSHOT.jar upload-service.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/upload-service.jar"]