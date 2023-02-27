# Build stage
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
# Copy pom first in order to cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ /app/src
RUN mvn package -DskipTests

# Run Jar stage
FROM openjdk:17
WORKDIR /app
COPY --from=build /app/target/slAIds-1.0.0.jar /app
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/slAIds-1.0.0.jar"]