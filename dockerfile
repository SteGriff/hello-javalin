# Gradle Build
FROM gradle:latest AS build
WORKDIR /app
COPY . .
RUN gradle build

# Runtime
FROM eclipse-temurin:25-alpine
COPY --from=build /app/build/libs/hello-javalin-1.0-SNAPSHOT-all.jar /app/hello-javalin.jar
EXPOSE 7070 
ENTRYPOINT ["java", "-jar", "/app/hello-javalin.jar"]
