-- =====================================================
-- PARTE 1: DADOS BASE E ESTRUTURA INICIAL
-- Sistema de Gestão de Freelancers
-- =====================================================

\c freelancer_db;

-- =====================================================
-- LIMPAR DADOS EXISTENTES
-- =====================================================
TRUNCATE TABLE log CASCADE;
TRUNCATE TABLE notificacao CASCADE;
TRUNCATE TABLE comentario CASCADE;
TRUNCATE TABLE pagamento CASCADE;
TRUNCATE TABLE entrega CASCADE;
TRUNCATE TABLE tarefa CASCADE;
TRUNCATE TABLE atividade CASCADE;
TRUNCATE TABLE contrato CASCADE;
TRUNCATE TABLE projeto_freelancer CASCADE;
TRUNCATE TABLE projeto CASCADE;
TRUNCATE TABLE freelancer CASCADE;
TRUNCATE TABLE empresa CASCADE;
TRUNCATE TABLE pessoa CASCADE;

-- Reset sequences
ALTER SEQUENCE pessoa_id_seq RESTART WITH 1;
ALTER SEQUENCE projeto_id_seq RESTART WITH 1;
ALTER SEQUENCE atividade_id_seq RESTART WITH 1;
ALTER SEQUENCE tarefa_id_seq RESTART WITH 1;
ALTER SEQUENCE entrega_id_seq RESTART WITH 1;
ALTER SEQUENCE pagamento_id_seq RESTART WITH 1;
ALTER SEQUENCE comentario_id_seq RESTART WITH 1;
ALTER SEQUENCE notificacao_id_seq RESTART WITH 1;
ALTER SEQUENCE log_id_seq RESTART WITH 1;
ALTER SEQUENCE contrato_id_seq RESTART WITH 1;

-- =====================================================
-- PESSOAS (Senha: senha123)
-- =====================================================
INSERT INTO pessoa (email, senha, tipo_usuario, ativo) VALUES
-- Empresas
('empresa1@teste.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'EMPRESA', TRUE),
('empresa2@teste.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'EMPRESA', TRUE),
-- Freelancers
('joao.designer@teste.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'FREELANCER', TRUE),
('maria.dev@teste.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'FREELANCER', TRUE),
('pedro.redator@teste.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'FREELANCER', TRUE),
('ana.marketing@teste.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'FREELANCER', TRUE),

-- =====================================================
-- EMPRESAS
-- =====================================================
INSERT INTO empresa (id, nome_empresa, cnpj, telefone, endereco) VALUES
(1, 'Tech Solutions Ltda', '12345678000190', '(11) 98765-4321', 'Av. Paulista, 1000 - São Paulo, SP'),
(2, 'Marketing Digital Pro', '98765432000101', '(21) 91234-5678', 'Rua do Ouvidor, 50 - Rio de Janeiro, RJ');

-- =====================================================
-- FREELANCERS
-- =====================================================
INSERT INTO freelancer (id, nome_completo, cpf_cnpj, is_pj, habilidades, valor_hora, portfolio_url) VALUES
(3, 'João Silva', '12345678901', FALSE, 'Design Gráfico, UI/UX, Figma, Adobe XD', 80.00, 'https://portfolio-joao.com'),
(4, 'Maria Santos', '12345678000123', TRUE, 'Desenvolvimento Web, React, Node.js, PostgreSQL', 120.00, 'https://github.com/maria-dev'),
(5, 'Pedro Oliveira', '98765432109', FALSE, 'Copywriting, SEO, Marketing de Conteúdo', 60.00, 'https://medium.com/@pedro-redator'),
(6, 'Ana Costa', '98765432000145', TRUE, 'Social Media, Gestão de Tráfego, Google Ads', 90.00, 'https://portfolio-ana.com');

-- =====================================================
-- PROJETOS
-- =====================================================
INSERT INTO projeto (empresa_id, titulo, descricao, orcamento_total, data_inicio, data_fim_prevista, status) VALUES
(1, 'Redesign do Site Corporativo', 'Modernização completa do site institucional com foco em UX', 15000.00, '2025-01-15', '2025-03-30', 'EM_ANDAMENTO'),
(1, 'Desenvolvimento de App Mobile', 'Aplicativo para gestão de vendas em campo', 45000.00, '2025-02-01', '2025-06-30', 'PLANEJAMENTO'),
(2, 'Campanha de Lançamento', 'Campanha completa para lançamento de novo produto', 8000.00, '2025-01-20', '2025-02-28', 'EM_ANDAMENTO');

-- =====================================================
-- PROJETO_FREELANCER
-- =====================================================
INSERT INTO projeto_freelancer (projeto_id, freelancer_id, papel, valor_acordado) VALUES
(1, 3, 'Designer Principal', 5000.00),
(1, 4, 'Desenvolvedor Frontend', 8000.00),
(2, 4, 'Desenvolvedor Full Stack', 30000.00),
(3, 5, 'Redator', 3000.00),
(3, 6, 'Gestor de Tráfego', 4500.00);

-- =====================================================
-- CONTRATOS
-- =====================================================
INSERT INTO contrato (projeto_id, termos, valor_total, data_inicio, data_fim, status, assinado_empresa, assinado_freelancer) VALUES
(1, 'Contrato de prestação de serviços para redesign do site corporativo. O freelancer se compromete a entregar todas as telas em até 60 dias. Pagamento será realizado por etapa concluída.', 13000.00, '2025-01-15', '2025-03-30', 'ATIVO', TRUE, TRUE),
(3, 'Contrato para campanha de lançamento incluindo criação de conteúdo e gestão de anúncios. Duração de 40 dias com entregas semanais.', 7500.00, '2025-01-20', '2025-02-28', 'ATIVO', TRUE, TRUE);

-- =====================================================
-- ATIVIDADES
-- =====================================================
INSERT INTO atividade (projeto_id, nome, descricao, ordem, status) VALUES
-- Projeto 1: Redesign do Site
(1, 'Pesquisa e Planejamento', 'Análise de concorrentes, personas e jornada do usuário', 1, 'CONCLUIDA'),
(1, 'Design de Interface', 'Criação de wireframes e protótipos de alta fidelidade', 2, 'EM_ANDAMENTO'),
(1, 'Desenvolvimento Frontend', 'Implementação do design em código React', 3, 'PENDENTE'),
(1, 'Testes e Ajustes', 'Testes de usabilidade e correções finais', 4, 'PENDENTE'),
-- Projeto 3: Campanha
(3, 'Planejamento da Campanha', 'Definição de estratégia e cronograma', 1, 'CONCLUIDA'),
(3, 'Criação de Conteúdo', 'Produção de textos e criativos', 2, 'EM_ANDAMENTO'),
(3, 'Configuração de Anúncios', 'Setup de campanhas no Google Ads e Meta', 3, 'PENDENTE');

-- =====================================================
-- TAREFAS
-- =====================================================
INSERT INTO tarefa (atividade_id, freelancer_id, titulo, descricao, prioridade, prazo, valor, status) VALUES
-- Atividade 1 (Concluída)
(1, 3, 'Análise de Concorrentes', 'Mapear 5 principais concorrentes e fazer benchmark', 'ALTA', '2025-01-20', 800.00, 'CONCLUIDA'),
(1, 3, 'Criação de Personas', 'Desenvolver 3 personas principais do público-alvo', 'ALTA', '2025-01-22', 600.00, 'CONCLUIDA'),
-- Atividade 2 (Em Andamento)
(2, 3, 'Wireframes Desktop', 'Criar wireframes de todas as páginas para desktop', 'ALTA', '2025-02-05', 1500.00, 'APROVADA'),
(2, 3, 'Wireframes Mobile', 'Criar wireframes responsivos para mobile', 'ALTA', '2025-02-10', 1200.00, 'ENTREGA_RECEBIDA'),
(2, 3, 'Protótipo Interativo', 'Desenvolver protótipo clicável no Figma', 'URGENTE', '2025-02-15', 1900.00, 'EM_PROGRESSO'),
-- Atividade 3 (Pendente)
(3, 4, 'Setup do Projeto React', 'Configurar projeto com Vite, TailwindCSS e bibliotecas', 'ALTA', '2025-02-20', 800.00, 'PENDENTE'),
(3, 4, 'Implementar Home Page', 'Desenvolver página inicial responsiva', 'ALTA', '2025-02-25', 1500.00, 'PENDENTE'),
-- Atividade 5 (Campanha - Concluída)
(5, 5, 'Briefing com Cliente', 'Reunião para entender objetivos e público', 'ALTA', '2025-01-22', 400.00, 'CONCLUIDA'),
(5, 6, 'Definir KPIs', 'Estabelecer métricas de sucesso da campanha', 'MEDIA', '2025-01-23', 300.00, 'CONCLUIDA'),
-- Atividade 6 (Campanha - Em Andamento)
(6, 5, 'Textos para Landing Page', 'Criar copy persuasivo para página de conversão', 'URGENTE', '2025-02-03', 900.00, 'APROVADA'),
(6, 5, 'Posts para Redes Sociais', 'Criar 20 posts para Instagram e Facebook', 'ALTA', '2025-02-08', 1200.00, 'EM_PROGRESSO'),
(6, 6, 'Banners para Anúncios', 'Criar 5 variações de banners para testes A/B', 'ALTA', '2025-02-10', 1100.00, 'PENDENTE');

-- =====================================================
-- PARTE 2: ENTREGAS, PAGAMENTOS, COMENTÁRIOS E LOGS
-- Sistema de Gestão de Freelancers
-- =====================================================

\c freelancer_db;

-- =====================================================
-- ENTREGAS
-- =====================================================
INSERT INTO entrega (tarefa_id, arquivo_url, observacoes, status, enviado_em, aprovado_em, aprovado_por) VALUES
-- Tarefas Concluídas
(1, '/uploads/analise-concorrentes.pdf', 'Análise completa com insights acionáveis', 'APROVADA', '2025-01-19 14:30:00', '2025-01-20 09:15:00', 1),
(2, '/uploads/personas.pdf', 'Três personas detalhadas com dores e objetivos', 'APROVADA', '2025-01-21 16:00:00', '2025-01-22 10:30:00', 1),
(3, '/uploads/wireframes-desktop-v1.fig', 'Primeira versão dos wireframes', 'REVISAO_NECESSARIA', '2025-02-03 11:00:00', NULL, NULL),
(3, '/uploads/wireframes-desktop-v2.fig', 'Wireframes ajustados conforme feedback', 'APROVADA', '2025-02-05 15:30:00', '2025-02-05 18:00:00', 1),
-- Tarefa Aguardando Aprovação
(4, '/uploads/wireframes-mobile.fig', 'Wireframes mobile responsivos', 'AGUARDANDO_APROVACAO', '2025-02-09 17:00:00', NULL, NULL),
-- Campanha
(8, '/uploads/briefing-campanha.pdf', 'Documento completo com objetivos e público', 'APROVADA', '2025-01-22 10:00:00', '2025-01-22 14:00:00', 2),
(10, '/uploads/copy-landing-page.docx', 'Textos otimizados para conversão', 'APROVADA', '2025-02-02 16:30:00', '2025-02-03 09:00:00', 2);

-- =====================================================
-- PAGAMENTOS
-- =====================================================
INSERT INTO pagamento (tarefa_id, freelancer_id, valor, data_pagamento, metodo_pagamento, status, observacoes) VALUES
-- Pagamentos Realizados
(1, 3, 800.00, '2025-01-25', 'PIX', 'PAGO', 'Pagamento pela análise de concorrentes'),
(2, 3, 600.00, '2025-01-28', 'PIX', 'PAGO', 'Pagamento pela criação de personas'),
(3, 3, 1500.00, '2025-02-06', 'TRANSFERENCIA', 'PAGO', 'Pagamento pelos wireframes desktop'),
(8, 5, 400.00, '2025-01-24', 'PIX', 'PAGO', 'Pagamento pelo briefing'),
(10, 5, 900.00, '2025-02-04', 'PIX', 'PAGO', 'Pagamento pelos textos da landing page'),
-- Pagamentos Pendentes
(4, 3, 1200.00, '2025-02-15', 'PIX', 'PENDENTE', 'Aguardando aprovação da entrega');

-- =====================================================
-- COMENTÁRIOS
-- =====================================================
INSERT INTO comentario (autor_id, tarefa_id, conteudo) VALUES
(1, 3, 'Primeira versão ficou boa, mas preciso que ajuste o header para destacar mais o CTA.'),
(3, 3, 'Perfeito! Já estou ajustando e envio nova versão até amanhã.'),
(1, 4, 'Aguardando análise dos wireframes mobile. Parece estar bem alinhado com o desktop!'),
(2, 10, 'Textos ficaram excelentes! Copy muito persuasivo, aprovado.'),
(5, 10, 'Obrigado! Qualquer ajuste é só avisar.');

-- =====================================================
-- NOTIFICAÇÕES
-- =====================================================
INSERT INTO notificacao (usuario_id, tipo, titulo, mensagem, lida, link_referencia) VALUES
-- Para Empresa 1
(1, 'ENTREGA_RECEBIDA', 'Nova Entrega Recebida', 'João Silva enviou os wireframes mobile para aprovação', FALSE, '/tarefas/4'),
(1, 'PRAZO_PROXIMO', 'Prazo Próximo', 'Tarefa "Protótipo Interativo" vence em 3 dias', FALSE, '/tarefas/5'),
-- Para Freelancers
(3, 'TAREFA_ATRIBUIDA', 'Nova Tarefa Atribuída', 'Você foi atribuído à tarefa "Protótipo Interativo"', TRUE, '/tarefas/5'),
(3, 'ENTREGA_APROVADA', 'Entrega Aprovada', 'Sua entrega dos wireframes desktop foi aprovada!', TRUE, '/tarefas/3'),
(3, 'PAGAMENTO_EFETUADO', 'Pagamento Realizado', 'Pagamento de R$ 1.500,00 foi efetuado', TRUE, '/pagamentos'),
(4, 'TAREFA_ATRIBUIDA', 'Novas Tarefas', 'Você tem 2 novas tarefas no projeto "Redesign do Site"', FALSE, '/projetos/1'),
(5, 'PAGAMENTO_EFETUADO', 'Pagamento Realizado', 'Pagamento de R$ 900,00 foi efetuado', TRUE, '/pagamentos');

-- =====================================================
-- LOGS
-- =====================================================
INSERT INTO log (usuario_id, entidade, entidade_id, acao, descricao, ip_address) VALUES
(1, 'PROJETO', 1, 'CRIOU', 'Criou projeto "Redesign do Site Corporativo"', '192.168.1.10'),
(1, 'TAREFA', 1, 'ATRIBUIU', 'Atribuiu tarefa "Análise de Concorrentes" para João Silva', '192.168.1.10'),
(3, 'ENTREGA', 1, 'CRIOU', 'Enviou entrega para tarefa "Análise de Concorrentes"', '192.168.1.25'),
(1, 'ENTREGA', 1, 'APROVOU', 'Aprovou entrega da análise de concorrentes', '192.168.1.10'),
(1, 'PAGAMENTO', 1, 'PAGOU', 'Registrou pagamento de R$ 800,00 para João Silva', '192.168.1.10'),
(2, 'PROJETO', 3, 'CRIOU', 'Criou projeto "Campanha de Lançamento"', '192.168.1.50'),
(5, 'ENTREGA', 7, 'CRIOU', 'Enviou entrega para tarefa "Textos para Landing Page"', '192.168.1.30');

-- =====================================================
-- ESTATÍSTICAS E MENSAGEM FINAL
-- =====================================================
DO $$
DECLARE
    total_pessoas INTEGER;
    total_empresas INTEGER;
    total_freelancers INTEGER;
    total_projetos INTEGER;
    total_atividades INTEGER;
    total_tarefas INTEGER;
    total_entregas INTEGER;
    total_pagamentos INTEGER;
    total_comentarios INTEGER;
    total_notificacoes INTEGER;
    total_logs INTEGER;
BEGIN
    SELECT COUNT(*) INTO total_pessoas FROM pessoa;
    SELECT COUNT(*) INTO total_empresas FROM empresa;
    SELECT COUNT(*) INTO total_freelancers FROM freelancer;
    SELECT COUNT(*) INTO total_projetos FROM projeto;
    SELECT COUNT(*) INTO total_atividades FROM atividade;
    SELECT COUNT(*) INTO total_tarefas FROM tarefa;
    SELECT COUNT(*) INTO total_entregas FROM entrega;
    SELECT COUNT(*) INTO total_pagamentos FROM pagamento;
    SELECT COUNT(*) INTO total_comentarios FROM comentario;
    SELECT COUNT(*) INTO total_notificacoes FROM notificacao;
    SELECT COUNT(*) INTO total_logs FROM log;

    RAISE NOTICE '';
    RAISE NOTICE '========================================';
    RAISE NOTICE '🌱 SEEDS INSERIDOS COM SUCESSO!';
    RAISE NOTICE '========================================';
    RAISE NOTICE '';
    RAISE NOTICE '📊 ESTATÍSTICAS:';
    RAISE NOTICE '  👥 Pessoas: %', total_pessoas;
    RAISE NOTICE '  🏢 Empresas: %', total_empresas;
    RAISE NOTICE '  👨‍💻 Freelancers: %', total_freelancers;
    RAISE NOTICE '  📁 Projetos: %', total_projetos;
    RAISE NOTICE '  📋 Atividades: %', total_atividades;
    RAISE NOTICE '  ✅ Tarefas: %', total_tarefas;
    RAISE NOTICE '  📦 Entregas: %', total_entregas;
    RAISE NOTICE '  💰 Pagamentos: %', total_pagamentos;
    RAISE NOTICE '  💬 Comentários: %', total_comentarios;
    RAISE NOTICE '  🔔 Notificações: %', total_notificacoes;
    RAISE NOTICE '  📝 Logs: %', total_logs;
    RAISE NOTICE '';
    RAISE NOTICE '🔑 CREDENCIAIS DE TESTE:';
    RAISE NOTICE '  Empresa 1: empresa1@teste.com / senha123';
    RAISE NOTICE '  Empresa 2: empresa2@teste.com / senha123';
    RAISE NOTICE '  Freelancer João: joao.designer@teste.com / senha123';
    RAISE NOTICE '  Freelancer Maria: maria.dev@teste.com / senha123';
    RAISE NOTICE '  Freelancer Pedro: pedro.redator@teste.com / senha123';
    RAISE NOTICE '  Freelancer Ana: ana.marketing@teste.com / senha123';
    RAISE NOTICE '';
    RAISE NOTICE '🎯 Sistema pronto para testes!';
    RAISE NOTICE '========================================';
END $$;