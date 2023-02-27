FROM eclipse-temurin:17
WORKDIR /app
# Copy pom first in order to cache dependencies
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline
COPY src/ /app/src
RUN ./mvnw package -DskipTests
COPY /app/target/slAIds-1.0.0.jar /app
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/slAIds-1.0.0.jar"]