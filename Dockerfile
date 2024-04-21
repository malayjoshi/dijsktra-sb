# Use JDK 21 base image
FROM eclipse-temurin:21-jdk-alpine
FROM maven

# Set the working directory in the container
WORKDIR /app

# Copy the Maven executable to the container image
COPY .mvn .mvn



# Copy the project descriptor files
COPY pom.xml .

# Download the Maven dependencies (not the project dependencies)
RUN mvn dependency:go-offline -B

# Copy the project source
COPY src src

# Build the Spring Boot application
RUN ./mvn package -DskipTests

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "target/CalculationService-1.0-SNAPSHOT.jar"]
