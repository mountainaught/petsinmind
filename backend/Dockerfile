# Build stage (using Gradle)
FROM gradle:8.4-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle build -x test  # -x test skips tests

# Run stage
FROM openjdk:17-jdk
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]