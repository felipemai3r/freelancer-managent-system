-- =====================================================
-- PARTE 1: TABELAS BASE E RELACIONAMENTOS PRINCIPAIS
-- Sistema de Gestão de Freelancers
-- =====================================================

\c freelancer_db;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- =====================================================
-- 1. PESSOA (Base)
-- =====================================================
CREATE TABLE IF NOT EXISTS pessoa (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    tipo_usuario VARCHAR(20) NOT NULL CHECK (tipo_usuario IN ('ADMIN', 'EMPRESA', 'FREELANCER')),
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_pessoa_email ON pessoa(email);
CREATE INDEX idx_pessoa_tipo ON pessoa(tipo_usuario);

-- =====================================================
-- 2. EMPRESA
-- =====================================================
CREATE TABLE IF NOT EXISTS empresa (
    id BIGINT PRIMARY KEY REFERENCES pessoa(id) ON DELETE CASCADE,
    nome_empresa VARCHAR(255) NOT NULL,
    cnpj VARCHAR(14) UNIQUE NOT NULL,
    telefone VARCHAR(15),
    endereco TEXT
);

CREATE UNIQUE INDEX idx_empresa_cnpj ON empresa(cnpj);

-- =====================================================
-- 3. FREELANCER
-- =====================================================
CREATE TABLE IF NOT EXISTS freelancer (
    id BIGINT PRIMARY KEY REFERENCES pessoa(id) ON DELETE CASCADE,
    nome_completo VARCHAR(255) NOT NULL,
    cpf_cnpj VARCHAR(14) UNIQUE NOT NULL,
    is_pj BOOLEAN DEFAULT FALSE,
    habilidades TEXT,
    valor_hora DECIMAL(10,2),
    portfolio_url VARCHAR(500)
);

CREATE UNIQUE INDEX idx_freelancer_doc ON freelancer(cpf_cnpj);
CREATE INDEX idx_freelancer_pj ON freelancer(is_pj);

-- =====================================================
-- 4. PROJETO
-- =====================================================
CREATE TABLE IF NOT EXISTS projeto (
    id BIGSERIAL PRIMARY KEY,
    empresa_id BIGINT NOT NULL REFERENCES empresa(id) ON DELETE CASCADE,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    orcamento_total DECIMAL(12,2),
    data_inicio DATE,
    data_fim_prevista DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'PLANEJAMENTO' 
        CHECK (status IN ('PLANEJAMENTO', 'EM_ANDAMENTO', 'CONCLUIDO', 'CANCELADO')),
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_projeto_empresa ON projeto(empresa_id);
CREATE INDEX idx_projeto_status ON projeto(status);
CREATE INDEX idx_projeto_datas ON projeto(data_inicio, data_fim_prevista);

-- =====================================================
-- 5. PROJETO_FREELANCER
-- =====================================================
CREATE TABLE IF NOT EXISTS projeto_freelancer (
    projeto_id BIGINT NOT NULL REFERENCES projeto(id) ON DELETE CASCADE,
    freelancer_id BIGINT NOT NULL REFERENCES freelancer(id) ON DELETE CASCADE,
    papel VARCHAR(100) NOT NULL,
    valor_acordado DECIMAL(10,2),
    atribuido_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (projeto_id, freelancer_id)
);

CREATE INDEX idx_pf_freelancer ON projeto_freelancer(freelancer_id);

-- =====================================================
-- 6. CONTRATO
-- =====================================================
CREATE TABLE IF NOT EXISTS contrato (
    id BIGSERIAL PRIMARY KEY,
    projeto_id BIGINT UNIQUE NOT NULL REFERENCES projeto(id) ON DELETE CASCADE,
    termos TEXT NOT NULL,
    valor_total DECIMAL(12,2) NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ATIVO'
        CHECK (status IN ('ATIVO', 'CONCLUIDO', 'CANCELADO')),
    assinado_empresa BOOLEAN DEFAULT FALSE,
    assinado_freelancer BOOLEAN DEFAULT FALSE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX idx_contrato_projeto ON contrato(projeto_id);

-- =====================================================
-- 7. ATIVIDADE
-- =====================================================
CREATE TABLE IF NOT EXISTS atividade (
    id BIGSERIAL PRIMARY KEY,
    projeto_id BIGINT NOT NULL REFERENCES projeto(id) ON DELETE CASCADE,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    ordem INTEGER NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE'
        CHECK (status IN ('PENDENTE', 'EM_ANDAMENTO', 'CONCLUIDA', 'CANCELADA')),
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(projeto_id, ordem)
);

CREATE INDEX idx_atividade_projeto ON atividade(projeto_id);
CREATE INDEX idx_atividade_status ON atividade(status);
CREATE INDEX idx_atividade_ordem ON atividade(projeto_id, ordem);

-- =====================================================
-- FIM DA PARTE 1
-- =====================================================
-- =====================================================
-- PARTE 2: TABELAS FINAIS, TRIGGERS E VIEWS
-- Sistema de Gestão de Freelancers
-- =====================================================

\c freelancer_db;

-- =====================================================
-- 8. TAREFA
-- =====================================================
CREATE TABLE IF NOT EXISTS tarefa (
    id BIGSERIAL PRIMARY KEY,
    atividade_id BIGINT NOT NULL REFERENCES atividade(id) ON DELETE CASCADE,
    freelancer_id BIGINT NOT NULL REFERENCES freelancer(id) ON DELETE RESTRICT,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    prioridade VARCHAR(20) NOT NULL DEFAULT 'MEDIA'
        CHECK (prioridade IN ('BAIXA', 'MEDIA', 'ALTA', 'URGENTE')),
    prazo DATE,
    valor DECIMAL(10,2),
    status VARCHAR(30) NOT NULL DEFAULT 'PENDENTE'
        CHECK (status IN ('PENDENTE', 'EM_PROGRESSO', 'AGUARDANDO_ENTREGA', 
                         'ENTREGA_RECEBIDA', 'APROVADA', 'REVISAO_NECESSARIA', 
                         'CONCLUIDA', 'CANCELADA')),
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_tarefa_atividade ON tarefa(atividade_id);
CREATE INDEX idx_tarefa_freelancer ON tarefa(freelancer_id);
CREATE INDEX idx_tarefa_status ON tarefa(status);
CREATE INDEX idx_tarefa_prazo ON tarefa(prazo);
CREATE INDEX idx_tarefa_prioridade ON tarefa(prioridade);

-- =====================================================
-- 9. ENTREGA
-- =====================================================
CREATE TABLE IF NOT EXISTS entrega (
    id BIGSERIAL PRIMARY KEY,
    tarefa_id BIGINT NOT NULL REFERENCES tarefa(id) ON DELETE CASCADE,
    arquivo_url VARCHAR(500) NOT NULL,
    observacoes TEXT,
    status VARCHAR(30) NOT NULL DEFAULT 'AGUARDANDO_APROVACAO'
        CHECK (status IN ('AGUARDANDO_APROVACAO', 'APROVADA', 
                         'REVISAO_NECESSARIA', 'REJEITADA')),
    enviado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    aprovado_em TIMESTAMP,
    aprovado_por BIGINT REFERENCES pessoa(id) ON DELETE SET NULL
);

CREATE INDEX idx_entrega_tarefa ON entrega(tarefa_id);
CREATE INDEX idx_entrega_status ON entrega(status);
CREATE INDEX idx_entrega_data ON entrega(enviado_em);

-- =====================================================
-- 10. PAGAMENTO
-- =====================================================
CREATE TABLE IF NOT EXISTS pagamento (
    id BIGSERIAL PRIMARY KEY,
    tarefa_id BIGINT REFERENCES tarefa(id) ON DELETE SET NULL,
    projeto_id BIGINT REFERENCES projeto(id) ON DELETE SET NULL,
    freelancer_id BIGINT NOT NULL REFERENCES freelancer(id) ON DELETE RESTRICT,
    valor DECIMAL(10,2) NOT NULL CHECK (valor > 0),
    data_pagamento DATE NOT NULL,
    metodo_pagamento VARCHAR(20) NOT NULL
        CHECK (metodo_pagamento IN ('PIX', 'TRANSFERENCIA', 'DINHEIRO', 'OUTRO')),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE'
        CHECK (status IN ('PENDENTE', 'PROCESSANDO', 'PAGO', 'CANCELADO')),
    observacoes TEXT,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CHECK (tarefa_id IS NOT NULL OR projeto_id IS NOT NULL)
);

CREATE INDEX idx_pagamento_tarefa ON pagamento(tarefa_id);
CREATE INDEX idx_pagamento_projeto ON pagamento(projeto_id);
CREATE INDEX idx_pagamento_freelancer ON pagamento(freelancer_id);
CREATE INDEX idx_pagamento_data ON pagamento(data_pagamento);
CREATE INDEX idx_pagamento_status ON pagamento(status);

-- =====================================================
-- 11. COMENTARIO
-- =====================================================
CREATE TABLE IF NOT EXISTS comentario (
    id BIGSERIAL PRIMARY KEY,
    autor_id BIGINT NOT NULL REFERENCES pessoa(id) ON DELETE CASCADE,
    tarefa_id BIGINT REFERENCES tarefa(id) ON DELETE CASCADE,
    atividade_id BIGINT REFERENCES atividade(id) ON DELETE CASCADE,
    conteudo TEXT NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CHECK (tarefa_id IS NOT NULL OR atividade_id IS NOT NULL)
);

CREATE INDEX idx_comentario_autor ON comentario(autor_id);
CREATE INDEX idx_comentario_tarefa ON comentario(tarefa_id);
CREATE INDEX idx_comentario_atividade ON comentario(atividade_id);
CREATE INDEX idx_comentario_data ON comentario(criado_em DESC);

-- =====================================================
-- 12. NOTIFICACAO
-- =====================================================
CREATE TABLE IF NOT EXISTS notificacao (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL REFERENCES pessoa(id) ON DELETE CASCADE,
    tipo VARCHAR(30) NOT NULL
        CHECK (tipo IN ('TAREFA_ATRIBUIDA', 'ENTREGA_RECEBIDA', 'ENTREGA_APROVADA',
                       'REVISAO_SOLICITADA', 'PAGAMENTO_EFETUADO', 'PRAZO_PROXIMO',
                       'PRAZO_VENCIDO', 'COMENTARIO_NOVO')),
    titulo VARCHAR(255) NOT NULL,
    mensagem TEXT NOT NULL,
    lida BOOLEAN DEFAULT FALSE,
    link_referencia VARCHAR(500),
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_notificacao_usuario ON notificacao(usuario_id);
CREATE INDEX idx_notificacao_lida ON notificacao(usuario_id, lida);
CREATE INDEX idx_notificacao_data ON notificacao(criado_em DESC);

-- =====================================================
-- 13. LOG
-- =====================================================
CREATE TABLE IF NOT EXISTS log (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT REFERENCES pessoa(id) ON DELETE SET NULL,
    entidade VARCHAR(50) NOT NULL,
    entidade_id BIGINT NOT NULL,
    acao VARCHAR(20) NOT NULL
        CHECK (acao IN ('CRIOU', 'ATUALIZOU', 'EXCLUIU', 'APROVOU', 'REJEITOU',
                       'PAGOU', 'CANCELOU', 'ATRIBUIU', 'COMENTOU')),
    descricao TEXT NOT NULL,
    dados_antigos JSONB,
    dados_novos JSONB,
    ip_address VARCHAR(45),
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_log_usuario ON log(usuario_id);
CREATE INDEX idx_log_entidade ON log(entidade, entidade_id);
CREATE INDEX idx_log_acao ON log(acao);
CREATE INDEX idx_log_data ON log(criado_em DESC);

-- =====================================================
-- TRIGGERS
-- =====================================================

CREATE OR REPLACE FUNCTION atualizar_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.atualizado_em = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_atualizar_pessoa
    BEFORE UPDATE ON pessoa
    FOR EACH ROW
    EXECUTE FUNCTION atualizar_timestamp();

CREATE TRIGGER trigger_atualizar_projeto
    BEFORE UPDATE ON projeto
    FOR EACH ROW
    EXECUTE FUNCTION atualizar_timestamp();

CREATE TRIGGER trigger_atualizar_atividade
    BEFORE UPDATE ON atividade
    FOR EACH ROW
    EXECUTE FUNCTION atualizar_timestamp();

CREATE TRIGGER trigger_atualizar_tarefa
    BEFORE UPDATE ON tarefa
    FOR EACH ROW
    EXECUTE FUNCTION atualizar_timestamp();

-- =====================================================
-- VIEWS
-- =====================================================

CREATE OR REPLACE VIEW v_projetos_resumo AS
SELECT 
    p.id,
    p.titulo,
    e.nome_empresa,
    p.status,
    p.orcamento_total,
    COUNT(DISTINCT a.id) as total_atividades,
    COUNT(DISTINCT t.id) as total_tarefas,
    COUNT(DISTINCT pf.freelancer_id) as total_freelancers
FROM projeto p
INNER JOIN empresa e ON p.empresa_id = e.id
LEFT JOIN atividade a ON p.id = a.projeto_id
LEFT JOIN tarefa t ON a.id = t.atividade_id
LEFT JOIN projeto_freelancer pf ON p.id = pf.projeto_id
GROUP BY p.id, p.titulo, e.nome_empresa, p.status, p.orcamento_total;

CREATE OR REPLACE VIEW v_tarefas_freelancer AS
SELECT 
    f.id as freelancer_id,
    f.nome_completo,
    t.id as tarefa_id,
    t.titulo,
    t.status,
    t.prazo,
    t.valor,
    p.titulo as projeto,
    e.nome_empresa
FROM freelancer f
INNER JOIN tarefa t ON f.id = t.freelancer_id
INNER JOIN atividade a ON t.atividade_id = a.id
INNER JOIN projeto p ON a.projeto_id = p.id
INNER JOIN empresa e ON p.empresa_id = e.id;

-- =====================================================
-- MENSAGEM FINAL
-- =====================================================
DO $$
BEGIN
    RAISE NOTICE '✅ Schema criado com sucesso!';
    RAISE NOTICE '📊 13 tabelas criadas';
    RAISE NOTICE '🔗 Relacionamentos configurados';
    RAISE NOTICE '📈 Índices otimizados';
    RAISE NOTICE '⚡ Triggers ativos';
    RAISE NOTICE '👁️  Views criadas';
END $$;