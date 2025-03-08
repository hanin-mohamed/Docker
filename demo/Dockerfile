#Uses Java 17 as the base image
FROM openjdk:17-jdk

# Copies the app JAR file to the container
COPY target/demo-3.4.3.jar /app/demo.jar

# Sets the working directory to /app
WORKDIR /app


# Exposes the port 8080
EXPOSE 8080

# Runs the JAR file when the container is started
CMD ["java","-jar", "/app/demo.jar"]