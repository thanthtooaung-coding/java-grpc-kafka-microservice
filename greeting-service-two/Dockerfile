FROM openjdk:17-jre-slim
WORKDIR /app

COPY target/greeting-service-two-1.0.0.jar app.jar

EXPOSE 9092

ENTRYPOINT ["java", "-jar", "app.jar"]
