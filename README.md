# Sistema de Gestão de Freelancers

Sistema web para controle e organização de freelancers, otimizando a gestão de entregas e pagamentos entre empresas contratantes e profissionais autônomos.

## 🚀 Tecnologias

- **Backend:** Java 21 + Spring Boot 3.2
- **Banco de Dados:** PostgreSQL 15
- **Containerização:** Docker + Docker Compose
- **Arquitetura:** REST API + MVC

## 📋 Pré-requisitos

- Docker Desktop
- Java 21 (para desenvolvimento local)
- Maven 3.9+

## 🔧 Setup Rápido
```bash
# Clone o repositório
git clone https://github.com/seu-usuario/freelancer-management-system.git

# Entre na pasta
cd freelancer-management-system

# Configure variáveis de ambiente
cp .env.example .env

# Suba os containers
docker-compose up -d

# Acesse
Backend: http://localhost:8080
Banco: localhost:5432
```

## 📊 Estrutura do Banco

- 13 tabelas principais
- Relacionamentos N:N
- Sistema de logs e auditoria
- Views otimizadas

## 🔑 Credenciais de Teste

**Empresa:**
- Email: empresa1@teste.com
- Senha: senha123

**Freelancer:**
- Email: joao.designer@teste.com
- Senha: senha123

## 📚 Documentação

Ver pasta `/docs` para diagramas e documentação detalhada.

## 👨‍💻 Autor

Felipe Maier - Projeto Acadêmico

## 📄 Licença

Este projeto é acadêmico.