# ===== STAGE 1: BUILD =====
FROM node:18-alpine AS build

WORKDIR /app

# Copiar package.json e package-lock.json
COPY package*.json ./

# Instalar dependências
RUN npm ci --silent

# Copiar código fonte
COPY . .

# Build de produção
RUN npm run build

# ===== STAGE 2: SERVE COM NGINX =====
FROM nginx:alpine

# Copiar configuração customizada do nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copiar arquivos buildados
COPY --from=build /app/build /usr/share/nginx/html

# Expor porta
EXPOSE 80

# Comando padrão do nginx
CMD ["nginx", "-g", "daemon off;"]