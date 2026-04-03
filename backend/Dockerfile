# Build stage using Maven official image
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy Maven project files and source code
COPY backend/pom.xml ./
COPY backend/src ./src

# Build the application
RUN mvn clean package -DskipTests -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/paws-adopt-backend-1.0.0.jar ./

EXPOSE 8080
CMD ["java", "-jar", "paws-adopt-backend-1.0.0.jar"]