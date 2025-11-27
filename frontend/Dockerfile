# Dockerfile Universal para React (Vite ou Create React App)
# Coloque em: frontend/Dockerfile

# ==========================================
# ESTÁGIO 1: BUILD
# ==========================================
FROM node:18-alpine AS build

WORKDIR /app

# Copiar package.json e package-lock.json
COPY package*.json ./

# Instalar dependências
RUN npm ci --legacy-peer-deps || npm install --legacy-peer-deps

# Copiar código fonte
COPY . .

# Criar arquivo .env.production se não existir
RUN echo "VITE_API_URL=http://localhost:8080" > .env.production || true
RUN echo "REACT_APP_API_URL=http://localhost:8080" >> .env.production || true

# Build da aplicação
RUN npm run build

# Verificar qual pasta foi gerada e copiar para /app/dist
RUN if [ -d "build" ]; then \
      echo "Create React App detectado (pasta build)"; \
      mv build dist; \
    elif [ -d "dist" ]; then \
      echo "Vite detectado (pasta dist)"; \
    else \
      echo "ERRO: Nenhuma pasta de build encontrada!"; \
      ls -la; \
      exit 1; \
    fi

# ==========================================
# ESTÁGIO 2: NGINX
# ==========================================
FROM nginx:alpine

# Remover configuração padrão do nginx
RUN rm -rf /usr/share/nginx/html/*

# Copiar build do estágio anterior
COPY --from=build /app/dist /usr/share/nginx/html

# Copiar configuração customizada do nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Criar script de inicialização para substituir variáveis de ambiente
RUN echo '#!/bin/sh' > /docker-entrypoint.sh && \
    echo 'echo "Iniciando frontend..."' >> /docker-entrypoint.sh && \
    echo 'echo "API_URL: $REACT_APP_API_URL"' >> /docker-entrypoint.sh && \
    echo 'nginx -g "daemon off;"' >> /docker-entrypoint.sh && \
    chmod +x /docker-entrypoint.sh

# Expor porta 80
EXPOSE 80

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD wget --quiet --tries=1 --spider http://localhost/ || exit 1

# Iniciar nginx
CMD ["/docker-entrypoint.sh"]
