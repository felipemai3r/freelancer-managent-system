# ⚡ Setup Rápido - 5 Minutos

Guia mínimo para rodar o projeto rapidamente.

---

## 1️⃣ Pré-requisitos

```bash
# Instalar Docker Desktop
# Mac: https://docs.docker.com/desktop/install/mac-install/
# Windows: https://docs.docker.com/desktop/install/windows-install/

# Verificar instalação
docker --version
docker-compose --version
```

---

## 2️⃣ Clonar e Configurar

```bash
# Clonar repositório
git clone https://github.com/seu-usuario/freelancer-management-system.git
cd freelancer-management-system

# Configurar ambiente
cp .env.example .env
```

---

## 3️⃣ Iniciar Docker

```bash
# Subir tudo
docker-compose up -d

# Aguardar ~2 minutos para build inicial

# Ver logs
docker-compose logs -f
```

---

## 4️⃣ Verificar

```bash
# Testar backend
curl http://localhost:8080/actuator/health

# Deve retornar: {"status":"UP"}
```

---

## 5️⃣ Acessar

**Backend API:**
```
http://localhost:8080
```

**Usuários de Teste:**
```
Empresa:    empresa1@teste.com / senha123
Freelancer: joao.designer@teste.com / senha123
```

---

## 🔄 Comandos Diários

```bash
# Iniciar
docker-compose up -d

# Parar
docker-compose down

# Ver logs
docker-compose logs -f

# Resetar (limpa banco)
docker-compose down -v
docker-compose up -d
```

---

## 🐛 Problema?

Ver guia completo em: [DOCKER.md](DOCKER.md)

Ou reiniciar tudo:
```bash
docker-compose down -v
docker-compose up -d --build
```

---

## 📚 Próximos Passos

1. Ver banco de dados:
```bash
docker exec -it freelancer_postgres psql -U admin -d freelancer_db
```

2. Testar API com Postman/Insomnia

3. Ler documentação completa no [README.md](README.md)