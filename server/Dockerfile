FROM openjdk:8-jdk-alpine
LABEL maintainer="t.p.d.anema@student.tudelft.nl"
VOLUME /tmp
EXPOSE 8088
ENV SPRING_PROFILES_ACTIVE=prod
ARG JAR_FILE=target/Server.jar
ADD ${JAR_FILE} Server.jar
ENTRYPOINT ["java", "-Dserver.use-forwarded-headers=true", "-jar", "/Server.jar"]
