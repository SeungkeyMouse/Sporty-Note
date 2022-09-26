FROM adoptopenjdk/openjdk11
CMD ["./mvnw", "clean", "package"]
ARG JAR_FILE_PATH=build/libs/*.jar
COPY ${JAR_FILE_PATH} server-0.0.1-SNAPSHOT.jar
#ENTRYPOINT ["java", "-jar", "server-0.0.1-SNAPSHOT.jar"]
ENTRYPOINT ["sh", "-c", "java -DJASYPT.ENCRYPTOR.PASSWORD=F2ABP2QP2MV9B70D9SAJB0A98BV9A73 -jar server-0.0.1-SNAPSHOT.jar"]