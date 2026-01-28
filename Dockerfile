FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY . .

# ✅ FIX: mvnw ko executable permission do
RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

# ✅ FINAL FIX: wildcard support
CMD sh -c "java -jar target/*.jar"
