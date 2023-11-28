# Use the official OpenJDK base image
FROM openjdk:17-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/boxing-fever-server-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Specify the command to run on container start
CMD ["java", "-jar", "app.jar"]