FROM gradle:7.5.0-jdk17 AS builder

WORKDIR /opt/app

COPY . .

# Build the application
RUN gradle build -x test

# Create the final image
FROM openjdk:17-jdk
WORKDIR /opt/app
COPY --from=builder /opt/app/build/libs/individualtrack-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar individualtrack-0.0.1-SNAPSHOT.jar"]
