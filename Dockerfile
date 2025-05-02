# Usa un JDK 17 oficial
FROM eclipse-temurin:17-jdk-focal

# Establece el directorio de trabajo
WORKDIR /app

# Copia Maven Wrapper y pom.xml para cachear dependencias
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copia el código fuente y compílalo
COPY src src
RUN ./mvnw clean package -DskipTests -B

# Expone el puerto (Render inyecta $PORT)
EXPOSE 8081

# Comando de arranque
CMD ["java","-Dserver.port=${PORT}","-jar","target/task-manager-backend.jar"]
