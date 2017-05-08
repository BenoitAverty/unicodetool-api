FROM openjdk:8

COPY ["build.gradle", "gradlew", "settings.gradle", "/unicodetool-server-build/"]
COPY ["src", "/unicodetool-server-build/src/"]
COPY ["gradle", "/unicodetool-server-build/gradle/"]

WORKDIR "/unicodetool-server-build"
RUN ["./gradlew", "bootJar"]
RUN ["sh", "-c", "cp /unicodetool-server-build/build/libs/*.jar /unicodetool-server.jar"]
RUN ["chmod", "755", "/unicodetool-server.jar"]

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE prod
ENV JAVA_OPTS -Xmx1g
ENTRYPOINT ["/unicodetool-server.jar"]