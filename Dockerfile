FROM maven:3.9.5-amazoncorretto-17 as builder

WORKDIR /home/back/api
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /home/back/api
COPY --from=builder /home/back/api/target/tdList-0.0.1-SNAPSHOT.jar ./tdList.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "tdList.jar" ]
