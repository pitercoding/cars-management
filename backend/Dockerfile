# Dockerfile for Spring Boot Backend

# ---------- Etapa 1: Build ----------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Instala Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Copia arquivos de build
COPY pom.xml .
COPY src ./src

# Build do projeto, ignorando testes
RUN mvn clean package -DskipTests

# ---------- Etapa 2: Imagem final ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copia o JAR gerado da etapa de build
COPY --from=build /app/target/*.jar ./app.jar

# Perfil ativo (produção)
ENV SPRING_PROFILES_ACTIVE=prod

# Variáveis do banco de dados (definidas no Render)
# Exemplo de nomes no Render:
# SPRING_DATASOURCE_URL
# SPRING_DATASOURCE_USERNAME
# SPRING_DATASOURCE_PASSWORD

# Porta exposta
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "./app.jar", \
            "--server.port=${PORT}", \
            "-Dspring.datasource.url=${SPRING_DATASOURCE_URL}", \
            "-Dspring.datasource.username=${SPRING_DATASOURCE_USERNAME}", \
            "-Dspring.datasource.password=${SPRING_DATASOURCE_PASSWORD}"]