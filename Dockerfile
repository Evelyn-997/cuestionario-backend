# ---------- BUILD STAGE ----------
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos solo pom primero para cachear dependencias
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw mvnw
RUN chmod +x mvnw && ./mvnw -q -DskipTests dependency:go-offline

# Copiamos el resto del código y compilamos
COPY src src
RUN ./mvnw -DskipTests clean package


# ---------- RUN STAGE ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copia el jar generado
COPY --from=build /app/target/*.jar app.jar

# Render inyecta PORT; nosotros lo usamos
ENV PORT=8080
EXPOSE 8080

# Arranca Spring en el puerto correcto
CMD ["sh", "-c", "java -Dserver.port=${PORT} -jar app.jar"]