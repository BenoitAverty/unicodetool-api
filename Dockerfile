FROM openjdk:8-alpine

COPY ["build.gradle", "gradlew", "settings.gradle", "/unicodetool-api-build/"]
COPY ["src", "/unicodetool-api-build/src/"]
COPY ["gradle", "/unicodetool-api-build/gradle/"]

WORKDIR "/unicodetool-api-build"
RUN ["./gradlew", "bootJar"]
RUN ["sh", "-c", "cp /unicodetool-api-build/build/libs/*.jar /unicodetool-api.jar"]
RUN ["chmod", "755", "/unicodetool-api.jar"]

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE prod
ENV JAVA_OPTS -Xmx1g
ENTRYPOINT ["java", "-jar", "/unicodetool-api.jar"]