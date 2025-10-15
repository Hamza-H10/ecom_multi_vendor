# Use the official Maven image with OpenJDK 21 as a base
FROM maven:3.9.5-openjdk-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files to the container
COPY . .

# Build the project and package it as a JAR file
RUN mvn clean package -DskipTests

# Use the official OpenJDK 21 image as a base for the runtime
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build stage to the runtime stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080 (or your configured port)
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]