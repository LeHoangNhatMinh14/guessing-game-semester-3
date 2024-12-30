# Stage 1: Build the application
FROM gradle:7.5.0-jdk17 AS builder

# Set the working directory inside the builder container
WORKDIR /opt/app

# Copy only Gradle build files first (for dependency caching)
COPY build.gradle settings.gradle ./

# Resolve Gradle dependencies
RUN gradle dependencies --no-daemon --stacktrace

# Copy the entire project into the builder container
COPY . .

# Build the application, excluding tests
RUN gradle build -x test --no-daemon --stacktrace

# Stage 2: Create the runtime image
FROM openjdk:17-jdk-slim

# Set the working directory inside the runtime container
WORKDIR /opt/app

# Copy the built JAR file from the builder stage
COPY --from=builder /opt/app/build/libs/individualtrack-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]
