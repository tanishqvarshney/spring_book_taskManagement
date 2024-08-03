FROM maven AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY . .
RUN mvn package -DskipTests
FROM openjdk:18-jdk-slim
WORKDIR /app/target
COPY --from=builder /app/target/user.jar .
EXPOSE 80
ENTRYPOINT ["java", "-jar", "user.jar"]