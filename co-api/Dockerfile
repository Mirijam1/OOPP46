FROM openjdk:8-jdk-alpine
LABEL maintainer="t.p.d.anema@student.tudelft.nl"
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/CO-API.jar
ADD ${JAR_FILE} CO-API.jar
ENTRYPOINT ["java", "-Dserver.use-forwarded-headers=true", "-jar", "/CO-API.jar"]
