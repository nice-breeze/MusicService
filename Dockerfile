# File: Dockerfile
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /build
# Copy only pom first to leverage layer caching
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests packageLABEL authors="breeze"

FROM eclipse-temurin:17-jre
WORKDIR /app
# Copy the built Spring Boot jar from the builder stage
COPY --from=builder /build/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]

LABEL authors="breeze"