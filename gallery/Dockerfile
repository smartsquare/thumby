FROM openjdk:8-jre-alpine

VOLUME /tmp

EXPOSE 9000

ADD build/libs/gallery-1.0.0.jar gallery-service.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/gallery-service.jar"]
