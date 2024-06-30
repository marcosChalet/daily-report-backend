FROM maven:3.9.5-amazoncorretto-17 as builder
RUN yum update -y && yum install -y openssl
WORKDIR /tmp
COPY pom.xml .
COPY src ./src
RUN openssl genpkey -algorithm RSA -out src/main/resources/app.key -pkeyopt rsa_keygen_bits:2048 && openssl rsa -pubout -in src/main/resources/app.key -out src/main/resources/app.pub
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-alpine
WORKDIR /home/back/api
COPY --from=builder /tmp/target/tdList-0.0.1-SNAPSHOT.jar ./tdList.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "tdList.jar" ]
