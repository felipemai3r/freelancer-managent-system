# ===== STAGE 1: BUILD =====
FROM --platform=linux/arm64 maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

# Copiar arquivos do Maven
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Dar permissão
RUN chmod +x mvnw

# Baixar dependências
RUN ./mvnw dependency:go-offline -B || true

# Copiar código fonte
COPY src ./src

# Build do projeto
RUN ./mvnw clean package -DskipTests -B

# ===== STAGE 2: RUNTIME =====
FROM --platform=linux/arm64 eclipse-temurin:21-jre-alpine

WORKDIR /app

# Criar usuário não-root
RUN addgroup -g 1000 spring && adduser -u 1000 -G spring -s /bin/sh -D spring

# Criar diretórios
RUN mkdir -p /app/uploads /app/logs && \
    chown -R spring:spring /app

# Copiar JAR
COPY --from=build --chown=spring:spring /app/target/*.jar app.jar

# Trocar para usuário não-root
USER spring:spring

# Expor porta
EXPOSE 8080

# Healthcheck
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD wget --quiet --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Variáveis JVM
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Comando
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]