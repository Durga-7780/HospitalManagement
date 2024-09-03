# Step 1: Build the application using Maven with JDK 8
FROM maven:3.8.1-openjdk-8-slim AS build

# Copy the source code into the container
COPY . /app

# Set the working directory
WORKDIR /app

# Build the application without running tests
RUN mvn clean package -DskipTests

# Step 2: Create a lightweight image for the application using JDK 8
FROM openjdk:8-jdk-slim

# Copy the built JAR file from the build stage
COPY --from=build /app/target/diagnosis-0.0.1-SNAPSHOT.jar /app/diagnosis-0.0.1-SNAPSHOT.jar

# Set the working directory
WORKDIR /app

# Expose the port your application uses (replace 8080 with your application's port)
EXPOSE 2003

# Run the application
CMD ["java", "-jar", "your-app-name.jar"]
 