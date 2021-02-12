# Stage 0, "build-stage", based on Node.js, to build and compile the frontend
FROM maven:3.3.9-jdk-8 as build-stage
WORKDIR /app
COPY ./ /app/
RUN mvn clean install


# Stage 1, based on Nginx, to have only the compiled app, ready for production with Nginx
FROM openjdk:8-jdk-alpine
COPY --from=build-stage /app/target/app.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
