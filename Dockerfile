# Usa un JDK 21 oficial
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Cachea dependencias
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Construye el JAR
COPY src src
RUN ./mvnw clean package -DskipTests -B

EXPOSE 8081

CMD ["sh", "-c", "java -Dserver.port=${PORT} -jar target/*.jar"]

