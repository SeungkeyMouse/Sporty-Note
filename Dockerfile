FROM adoptopenjdk/openjdk11
CMD ["./mvnw", "clean", "package"]
ARG JAR_FILE_PATH=build/libs/*.jar
COPY ${JAR_FILE_PATH} server-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "server-0.0.3-SNAPSHOT.jar"]