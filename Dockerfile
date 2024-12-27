# Use an OpenJDK 21 base image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the compiled JAR file
COPY target/dineflow-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application listens on (default for Spring Boot is 8080)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
