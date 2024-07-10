# Build stage with Maven and OpenJDK 17
FROM maven:3.8.3-openjdk-17-slim AS build
WORKDIR /usr/src/app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -Pdocker

# Final stage with a smaller base image
FROM openjdk:17-alpine
WORKDIR /usr/app
COPY --from=build /usr/src/app/target/accounts-0.0.1-SNAPSHOT.jar ./accounts.jar
EXPOSE 8080

# Added JVM options for optimization
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "accounts.jar"]