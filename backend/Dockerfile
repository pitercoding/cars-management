# Dockerfile for Spring Boot Backend

# ---------- Etapa 1: Build ----------
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia arquivos de build
COPY pom.xml .
COPY src ./src

# Build do projeto, ignorando testes
RUN mvn -DskipTests package

# ---------- Etapa 2: Imagem final ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copia o JAR gerado da etapa de build
COPY --from=build /app/target/*.jar /app/app.jar

# Perfil ativo (producao)
ENV SPRING_PROFILES_ACTIVE=prod

# Porta exposta
EXPOSE 8080

# Comando de inicializacao
ENTRYPOINT ["java","-jar","/app/app.jar"]
