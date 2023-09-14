
FROM openjdk:11-jre-slim

WORKDIR /app

COPY wp-project-0.0.1-SNAPSHOT.jar .

EXPOSE 9090

CMD ["java", "-jar", "wp-project-0.0.1-SNAPSHOT.jar"]