FROM openjdk:17

WORKDIR /app
COPY src ./src
COPY .mvn ./.mvn
COPY aot-jar.properties micronaut-cli.yml mvnw mvnw.bat pom.xml ./
CMD ["./mvnw", "package"]