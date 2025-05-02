# Usa un JDK 21 oficial
FROM eclipse-temurin:21-jdk-focal

# Directorio de trabajo
WORKDIR /app

# Copia Maven Wrapper y pom.xml para cachear dependencias
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Descarga deps sin recompilar todo el código
RUN chmod +x mvnw \
 && ./mvnw dependency:go-offline -B

# Copia el código fuente y construye el JAR
COPY src src
RUN ./mvnw clean package -DskipTests -B

# Expone el puerto (Render inyecta $PORT)
EXPOSE 8081

# Arranca la aplicación
CMD ["java", "-Dserver.port=${PORT}", "-jar", "target/task-manager-backend.jar"]
