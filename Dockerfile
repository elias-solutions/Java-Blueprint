FROM eclipse-temurin:21-jre-noble AS base
WORKDIR /app

COPY ./target/springboot-java-blueprint-*.jar /app/app.jar

ENV SERVER_PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
