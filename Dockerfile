FROM openjdk:17

WORKDIR /app
COPY src ./src
COPY .mvn ./.mvn
COPY aot-jar.properties micronaut-cli.yml mvnw mvnw.bat pom.xml ./
RUN ["./mvnw", "-DskipTests", "-Dmicronaut.environments=prod", "package"]
EXPOSE 8080
CMD ["java", "-jar", "target/simple-marketplace-0.1.jar"]