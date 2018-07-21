FROM openjdk:8-jre-alpine

VOLUME /tmp

EXPOSE 8888

ADD build/libs/generator-1.0.0.jar generator-service.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/generator-service.jar"]
