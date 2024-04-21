FROM eclipse-temurin:21-jdk-alpine
FROM maven
VOLUME /tmp
RUN mvn install
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

