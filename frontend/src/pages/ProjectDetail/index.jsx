import React, { useEffect, useState, useCallback } from "react";
import { useParams } from "react-router-dom";
import ProjetoService from "../../api/projetoService";
import AtividadeService from "../../api/atividadeService";
import TarefaService from "../../api/tarefaService";
import ProjetoFreelancerService from "../../api/projetoFreelancerService";

import ActivityCard from "../../components/ActivityCard";
import styles from "./ProjectDetail.module.css";
import "animate.css";

/**
 * ProjectDetail.jsx
 * - busca projeto e suas atividades
 * - permite criar atividade
 * - permite mover atividades (up/down)
 * - permite criar tarefa dentro de atividade
 * - permite toggle status de tarefa
 *
 * Observação: alguns endpoints dependem do backend; há fallbacks comentados.
 */

export default function ProjectDetail() {
  const { id } = useParams();
  const [projeto, setProjeto] = useState(null);
  const [atividades, setAtividades] = useState([]);
  const [freelancers, setFreelancers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [creatingActivity, setCreatingActivity] = useState(false);

  const fetchProjeto = useCallback(async () => {
    setLoading(true);
    try {
      // tenta GET /api/projetos/{id}
      const res = await ProjetoService.getById(id);
      setProjeto(res.data);
    } catch (err) {
      // fallback: listar e filtrar (caso backend ainda não tenha getById)
      try {
        const list = await ProjetoService.list();
        const found = list.data.find(p => String(p.id) === String(id));
        setProjeto(found || null);
      } catch (e) {
        console.error("Erro ao carregar projeto", e);
      }
    } finally {
      setLoading(false);
    }
  }, [id]);

  const fetchAtividades = useCallback(async () => {
    try {
      const res = await AtividadeService.listByProject(id);
      // ordenar por ordem
      const ordered = (res.data || []).sort((a,b) => (a.ordem ?? 0) - (b.ordem ?? 0));
      setAtividades(ordered);
    } catch (err) {
      console.warn("Erro ao carregar atividades:", err);
      setAtividades([]);
    }
  }, [id]);

  const fetchFreelancers = useCallback(async () => {
    try {
      const res = await ProjetoFreelancerService.listByProject(id);
      setFreelancers(res.data || []);
    } catch (err) {
      setFreelancers([]);
    }
  }, [id]);

  useEffect(() => {
    fetchProjeto();
    fetchAtividades();
    fetchFreelancers();
  }, [fetchProjeto, fetchAtividades, fetchFreelancers]);

  const handleCreateActivity = async () => {
    const nome = window.prompt("Nome da nova atividade:");
    if (!nome) return;
    try {
      await AtividadeService.createForProject(id, { nome, descricao: "", ordem: (atividades.length + 1) });
      await fetchAtividades();
    } catch (err) {
      console.error("Erro ao criar atividade", err);
      alert("Erro ao criar atividade");
    }
  };

  const handleMoveUp = async (atividadeId) => {
    try {
      await AtividadeService.moveUp(atividadeId);
      await fetchAtividades();
    } catch (err) {
      console.error(err);
      alert("Erro ao mover atividade");
    }
  };

  const handleMoveDown = async (atividadeId) => {
    try {
      await AtividadeService.moveDown(atividadeId);
      await fetchAtividades();
    } catch (err) {
      console.error(err);
      alert("Erro ao mover atividade");
    }
  };

  // criar tarefa simples via prompt (pode trocar para modal)
  const handleAddTask = async (atividadeId) => {
    const titulo = window.prompt("Título da tarefa:");
    if (!titulo) return;
    try {
      await TarefaService.createForAtividade(atividadeId, { titulo, descricao: "", prioridade: "MEDIA" });
      await fetchAtividades(); // atividade vem com tarefas se endpoint retornar
    } catch (err) {
      console.error("Erro ao adicionar tarefa", err);
      alert("Erro ao adicionar tarefa");
    }
  };

  const handleToggleTaskStatus = async (tarefa) => {
    try {
      const novo = tarefa.status === "CONCLUIDA" ? "EM_ANDAMENTO" : "CONCLUIDA";
      await TarefaService.setStatus(tarefa.id, novo);
      await fetchAtividades();
    } catch (err) {
      console.error("Erro ao atualizar status da tarefa", err);
      alert("Erro ao atualizar status");
    }
  };

  if (loading) return <p className={styles.loading}>Carregando projeto...</p>;
  if (!projeto) return <p className={styles.loading}>Projeto não encontrado</p>;

  const progresso = Number(projeto.calcularProgresso?.() ?? projeto.progress ?? projeto.progresso ?? 0).toFixed(0);

  return (
    <div className={`${styles.page} animate__animated animate__fadeInUp`}>
      <div className={styles.header}>
        <div>
          <h2 className={styles.title}>{projeto.titulo}</h2>
          <div className={styles.meta}>
            <span>{projeto.empresa?.nomeEmpresa || projeto.empresa?.nome_empresa}</span>
            <span> • Status: {projeto.status}</span>
            <span> • Progresso: {progresso}%</span>
          </div>
        </div>

        <div className={styles.actions}>
          <button className="btn" onClick={() => fetchProjeto()}>Atualizar</button>
          <button className="btn primary" onClick={handleCreateActivity}>Nova atividade</button>
        </div>
      </div>

      <section className={styles.section}>
        <h3 className={styles.sectionTitle}>Freelancers</h3>
        <div className={styles.freelancers}>
          {freelancers.length === 0 ? (
            <div className={styles.empty}>Nenhum freelancer atribuído</div>
          ) : (
            freelancers.map((pf) => (
              <div key={`${pf.id?.projetoId}-${pf.id?.freelancerId}`} className={styles.freelancerCard}>
                <div>
                  <div className={styles.freelancerName}>{pf.freelancer?.nomeCompleto || pf.freelancer?.nome}</div>
                  <div className={styles.freelancerMeta}>{pf.papel} • R$ {pf.valorAcordado}</div>
                </div>
              </div>
            ))
          )}
        </div>
      </section>

      <section className={styles.section}>
        <h3 className={styles.sectionTitle}>Atividades</h3>

        <div className={styles.activitiesGrid}>
          {atividades.length === 0 ? (
            <div className={styles.empty}>Nenhuma atividade cadastrada</div>
          ) : (
            atividades.map(a => (
              <ActivityCard
                key={a.id}
                atividade={a}
                onMoveUp={handleMoveUp}
                onMoveDown={handleMoveDown}
                onAddTask={handleAddTask}
                onToggleStatus={handleToggleTaskStatus}
              />
            ))
          )}
        </div>
      </section>
    </div>
  );
}
