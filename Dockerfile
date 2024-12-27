# Use an OpenJDK 21 base image
FROM openjdk:21-jdk-slim as builder

# Set the working directory for the build stage
WORKDIR /build

# Copy the Maven project files to the build stage
COPY pom.xml ./
COPY src ./src/

# Build the application using Maven
RUN apt-get update && apt-get install -y maven && \
    mvn -B package --file pom.xml -DskipTests

# Use a new base image for the runtime
FROM openjdk:21-jdk-slim

# Set the working directory for the runtime
WORKDIR /app

# Copy the compiled JAR file from the builder stage
COPY --from=builder /build/target/dineflow-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application listens on (default for Spring Boot is 8080)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
