FROM openjdk:17-jdk

COPY target/demo-3.4.3.jar /app/demo.jar

WORKDIR /app
EXPOSE 8080

CMD ["java","-jar", "/app/demo.jar"]
