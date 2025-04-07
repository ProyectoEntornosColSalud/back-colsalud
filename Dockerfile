FROM eclipse-temurin:21-jdk-alpine AS builder
ARG MS_NAME=fpc-backend
WORKDIR /app

COPY ./pom.xml ./mvnw ./mvnw.cmd /app/
COPY .mvn /app/.mvn/
RUN chmod +x mvnw
RUN ./mvnw dependency:resolve

COPY ./src /app/src/
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
RUN mkdir ./logs

COPY --from=builder /app/target/pep-1.0.jar /app/pep-1.0.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "pep-1.0.jar"]