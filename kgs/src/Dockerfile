FROM maven:3.9.8-eclipse-temurin-21 AS build

# Copy local code to the container image.
COPY . .

# Build a release artifact.
RUN mvn package -DskipTests

# Use AdoptOpenJDK for base image.
FROM openjdk:21

# Copy the jar to the production image from the builder stage.
COPY --from=builder /target/kgs-*.war relink-kgs.war

# Run the web service on container startup.
EXPOSE 8081
CMD ["java", "-jar", "relink-kgs.jar"]
