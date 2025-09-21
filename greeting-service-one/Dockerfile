FROM openjdk:17-jre-slim
WORKDIR /app

COPY target/greeting-service-one-1.0.0.jar app.jar

EXPOSE 9091

ENTRYPOINT ["java", "-jar", "app.jar"]
