FROM eclipse-temurin:17-jre-focal
WORKDIR /app
COPY target/simple-service-0.0.1-SNAPSHOT.jar simple-service.jar
EXPOSE 9876
ENTRYPOINT ["java", "-jar", "simple-service.jar"]