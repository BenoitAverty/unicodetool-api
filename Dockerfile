FROM openjdk:8-alpine

COPY ["build/libs/*.jar", "/unicodetool-api.jar"]

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE prod
ENV JAVA_OPTS -Xmx1g
ENTRYPOINT ["java", "-jar", "/unicodetool-api.jar"]