FROM maven:3.9.5-amazoncorretto-21 as builder
WORKDIR /home/back/api
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /home/back/api
COPY --from=builder /home/back/api/target/daily-report-backend-0.0.1-SNAPSHOT.jar ./backendApi.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "backendApi.jar" ]
