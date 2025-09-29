# ---- Build Stage ----
# Use a full JDK to build the application and produce the JAR file.
FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew build -x test

# ---- Final Stage ----
# Use a minimal JRE for the final, lightweight production image.
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy only the built artifact from the build stage.
COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]