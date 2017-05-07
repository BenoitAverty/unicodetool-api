FROM openjdk:8-jre

COPY build/libs/unicodetool-server-* /unicodetool-server.jar
RUN ["chmod", "755", "/unicodetool-server.jar"]

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE prod
ENTRYPOINT ["/unicodetool-server.jar"]