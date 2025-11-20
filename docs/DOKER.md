# 🐳 Guia Completo: Docker

Este guia contém todas as instruções para rodar o projeto com Docker.

---

## 📋 Pré-requisitos

- **Docker Desktop** instalado e rodando
- **Docker Compose** instalado (geralmente vem com Docker Desktop)
- **8GB RAM** disponível (mínimo)
- **Portas livres:** 8080 (backend), 5432 (PostgreSQL), 5050 (pgAdmin)

### Verificar Instalação

```bash
# Verificar Docker
docker --version
# Deve retornar: Docker version 24.x.x ou superior

# Verificar Docker Compose
docker-compose --version
# Deve retornar: Docker Compose version v2.x.x ou superior

# Verificar se Docker está rodando
docker ps
# Se retornar lista (vazia ou não), está funcionando
```

---

## 🚀 Iniciar o Projeto (Primeira Vez)

### **Passo 1: Configurar Variáveis de Ambiente**

```bash
# Copiar arquivo de exemplo
cp .env.example .env

# Editar se necessário (opcional)
nano .env
# ou
code .env
```

### **Passo 2: Subir os Containers**

```bash
# Construir e iniciar todos os serviços
docker-compose up -d

# Explicação dos parâmetros:
# up      = inicia os containers
# -d      = modo detached (background)
# --build = reconstrói as imagens (use se mudou código)
```

### **Passo 3: Acompanhar os Logs**

```bash
# Ver logs de todos os serviços
docker-compose logs -f

# Ver apenas logs do backend
docker-compose logs -f backend

# Ver apenas logs do PostgreSQL
docker-compose logs -f postgres

# Parar de seguir logs: Ctrl + C
```

### **Passo 4: Verificar se Subiu Corretamente**

```bash
# Listar containers rodando
docker ps

# Deve mostrar:
# - freelancer_postgres (UP)
# - freelancer_backend (UP)

# Verificar saúde do backend
curl http://localhost:8080/actuator/health

# Deve retornar:
# {"status":"UP"}
```

### **Passo 5: Verificar Banco de Dados**

```bash
# Entrar no PostgreSQL
docker exec -it freelancer_postgres psql -U admin -d freelancer_db

# Dentro do psql:
\dt              # Listar tabelas (deve mostrar 13)
SELECT COUNT(*) FROM pessoa;  # Deve retornar: 6
\q               # Sair
```

---

## 🔄 Uso Diário (Após Primeira Configuração)

### **Iniciar o Ambiente**

```bash
# Subir containers (rápido, usa cache)
docker-compose up -d

# Verificar status
docker-compose ps
```

### **Parar o Ambiente**

```bash
# Parar containers (mantém volumes/dados)
docker-compose down

# Parar e REMOVER volumes (limpa banco de dados)
docker-compose down -v
```

### **Reiniciar Apenas um Serviço**

```bash
# Reiniciar backend
docker-compose restart backend

# Reiniciar PostgreSQL
docker-compose restart postgres
```

---

## 🔧 Comandos Úteis

### **Logs e Debug**

```bash
# Ver logs em tempo real
docker-compose logs -f

# Ver últimas 100 linhas de log
docker-compose logs --tail=100

# Ver logs de um container específico
docker logs freelancer_backend
docker logs freelancer_postgres

# Buscar erro nos logs
docker-compose logs | grep ERROR
docker-compose logs | grep Exception
```

### **Rebuild (Após Mudanças no Código)**

```bash
# Rebuild do backend
docker-compose build backend

# Rebuild tudo
docker-compose build

# Rebuild e reiniciar
docker-compose up -d --build

# Rebuild sem cache (mais lento, mas limpo)
docker-compose build --no-cache
```

### **Acessar Terminal dos Containers**

```bash
# Entrar no container do backend
docker exec -it freelancer_backend sh

# Entrar no container do PostgreSQL
docker exec -it freelancer_postgres bash

# Executar comando sem entrar
docker exec freelancer_postgres psql -U admin -d freelancer_db -c "SELECT * FROM pessoa;"
```

### **Gerenciar Banco de Dados**

```bash
# Backup do banco
docker exec freelancer_postgres pg_dump -U admin freelancer_db > backup.sql

# Restaurar backup
docker exec -i freelancer_postgres psql -U admin -d freelancer_db < backup.sql

# Resetar banco (CUIDADO: apaga tudo)
docker-compose down -v
docker-compose up -d
```

### **Limpar Recursos Docker**

```bash
# Ver uso de espaço
docker system df

# Limpar containers parados
docker container prune

# Limpar imagens não usadas
docker image prune

# Limpar volumes órfãos
docker volume prune

# LIMPAR TUDO (CUIDADO!)
docker system prune -a --volumes
```

---

## 🔍 Verificação de Saúde

### **Checklist Completo**

```bash
# 1. Containers UP
docker ps | grep freelancer
# Deve mostrar 2 containers: postgres e backend

# 2. Backend respondendo
curl http://localhost:8080/actuator/health
# Resposta: {"status":"UP"}

# 3. Banco de dados acessível
docker exec -it freelancer_postgres psql -U admin -d freelancer_db -c "\dt"
# Deve listar 13 tabelas

# 4. Dados de teste presentes
docker exec -it freelancer_postgres psql -U admin -d freelancer_db -c "SELECT COUNT(*) FROM pessoa;"
# Deve retornar: 6

# 5. Backend conectado no banco
docker-compose logs backend | grep "Started ManagementSystemApplication"
# Deve mostrar mensagem de inicialização
```

---

## 🐛 Resolução de Problemas

### **Problema: Porta já em uso**

```bash
# Descobrir o que está usando a porta
lsof -i :8080  # Backend
lsof -i :5432  # PostgreSQL

# Matar processo
kill -9 <PID>

# OU mudar porta no .env
BACKEND_PORT=8081
```

### **Problema: Backend não conecta no banco**

```bash
# Ver logs do backend
docker-compose logs backend

# Ver se postgres está pronto
docker-compose logs postgres | grep "ready to accept connections"

# Reiniciar tudo
docker-compose down
docker-compose up -d
```

### **Problema: Banco não tem tabelas**

```bash
# Verificar se init.sql rodou
docker-compose logs postgres | grep "init.sql"

# Executar manualmente
docker exec -i freelancer_postgres psql -U admin -d freelancer_db < database/init.sql
docker exec -i freelancer_postgres psql -U admin -d freelancer_db < database/seed.sql
```

### **Problema: "Permission denied" ou "Cannot connect"**

```bash
# Resetar permissões dos volumes
docker-compose down -v
docker volume rm freelancer_postgres_data
docker-compose up -d

# Verificar se Docker Desktop está rodando
# Mac: Ícone no topo da tela
# Windows: Ícone na bandeja
```

### **Problema: Build muito lento**

```bash
# Limpar cache do Maven
docker-compose exec backend rm -rf ~/.m2/repository

# Rebuild sem cache
docker-compose build --no-cache backend
```

### **Problema: "Cannot allocate memory"**

```bash
# Aumentar memória do Docker Desktop
# Settings → Resources → Memory → 4GB ou mais

# Ou limpar recursos
docker system prune -a
```

---

## 🎯 Acessos Rápidos

### **URLs Importantes**

```
Backend API:         http://localhost:8080
Health Check:        http://localhost:8080/actuator/health
API Info:            http://localhost:8080/actuator/info
pgAdmin (opcional):  http://localhost:5050
```

### **Credenciais Padrão**

```
PostgreSQL:
  Host:     localhost (ou postgres dentro do Docker)
  Port:     5432
  Database: freelancer_db
  User:     admin
  Password: admin123

pgAdmin (se usar --profile dev):
  Email:    admin@freelancer.com
  Password: admin123

Usuários de Teste:
  Empresa:    empresa1@teste.com / senha123
  Freelancer: joao.designer@teste.com / senha123
```

---

## 📊 Monitoramento

### **Ver Recursos Usados**

```bash
# Ver uso de CPU e Memória
docker stats

# Ver apenas do projeto
docker stats freelancer_backend freelancer_postgres

# Parar monitoramento: Ctrl + C
```

### **Ver Volumes**

```bash
# Listar volumes
docker volume ls | grep freelancer

# Ver detalhes de um volume
docker volume inspect freelancer_postgres_data

# Ver tamanho dos volumes
docker system df -v | grep freelancer
```

---

## 🎓 Comandos Avançados

### **Desenvolvimento Local (Sem Docker)**

```bash
# Subir apenas o PostgreSQL
docker-compose up -d postgres

# Rodar backend localmente
cd backend
mvn spring-boot:run

# Backend conectará no PostgreSQL do Docker
```

### **Executar Testes**

```bash
# Rodar testes dentro do container
docker-compose exec backend mvn test

# Rodar testes específicos
docker-compose exec backend mvn test -Dtest=ClasseTest
```

### **Hot Reload (Desenvolvimento)**

```bash
# Montar código fonte como volume (editar docker-compose.yml)
volumes:
  - ./backend/src:/app/src
  - ./backend/target:/app/target

# Usar Spring DevTools (já configurado)
docker-compose up -d
# Mudanças no código recarregam automaticamente
```

---

## 📚 Recursos Adicionais

### **Documentação Oficial**

- [Docker Docs](https://docs.docker.com/)
- [Docker Compose Docs](https://docs.docker.com/compose/)
- [Spring Boot with Docker](https://spring.io/guides/topicals/spring-boot-docker/)

### **Comandos de Referência Rápida**

```bash
# Iniciar
docker-compose up -d

# Parar
docker-compose down

# Logs
docker-compose logs -f

# Rebuild
docker-compose up -d --build

# Resetar tudo
docker-compose down -v && docker-compose up -d

# Entrar no backend
docker exec -it freelancer_backend sh

# Entrar no banco
docker exec -it freelancer_postgres psql -U admin -d freelancer_db

# Status
docker-compose ps

# Limpar
docker system prune -a
```

---

## ✅ Checklist de Início

- [ ] Docker Desktop instalado e rodando
- [ ] Arquivo `.env` configurado
- [ ] Executado `docker-compose up -d`
- [ ] Containers UP: `docker ps`
- [ ] Backend respondendo: `curl localhost:8080/actuator/health`
- [ ] Banco com tabelas: `\dt` no psql
- [ ] Dados de teste presentes: `SELECT COUNT(*) FROM pessoa;`

---

## 🆘 Suporte

Se encontrar problemas:

1. Verificar logs: `docker-compose logs -f`
2. Verificar status: `docker-compose ps`
3. Tentar reiniciar: `docker-compose restart`
4. Último recurso: `docker-compose down -v && docker-compose up -d --build`

---

**Última atualização:** 2025-01-16