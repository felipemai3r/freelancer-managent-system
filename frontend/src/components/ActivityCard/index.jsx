import React, { useState } from "react";
import styles from "./ActivityCard.module.css";
import "animate.css";
import TaskItem from "../TaskItem";

export default function ActivityCard({
  atividade,
  onMoveUp,
  onMoveDown,
  onOpenTasks,
  onToggleStatus,
  onAddTask,
}) {
  const [expanded, setExpanded] = useState(false);

  const percentual = Number(atividade.percentualConcluido ?? atividade.percentual ?? 0).toFixed(0);

  return (
    <div className={`${styles.card} animate__animated animate__fadeInUp`}>
      <div className={styles.header}>
        <div>
          <h4 className={styles.title}>{atividade.nome}</h4>
          <div className={styles.meta}>
            <span>Ordem: {atividade.ordem}</span>
            <span> • Status: {atividade.status}</span>
            <span> • {percentual}% concluído</span>
          </div>
        </div>

        <div className={styles.controls}>
          <button className="btn" onClick={() => onMoveUp && onMoveUp(atividade.id)}>↑</button>
          <button className="btn" onClick={() => onMoveDown && onMoveDown(atividade.id)}>↓</button>
          <button className="btn" onClick={() => setExpanded(s => !s)}>{expanded ? "Ocultar" : "Ver tarefas"}</button>
        </div>
      </div>

      <div className={styles.progressWrapper}>
        <div className={styles.progressBar}>
          <div className={styles.progressFill} style={{ width: `${percentual}%` }} />
        </div>
      </div>

      {expanded && (
        <div className={styles.tasks}>
          { (atividade.tarefas && atividade.tarefas.length > 0) ? (
            atividade.tarefas.map(t => (
              <TaskItem key={t.id} tarefa={t} onToggle={() => onToggleStatus && onToggleStatus(t)} />
            ))
          ) : (
            <p className={styles.noTasks}>Nenhuma tarefa ainda.</p>
          )}

          <div style={{ marginTop: ".6rem" }}>
            <button className="btn primary" onClick={() => onAddTask && onAddTask(atividade.id)}>Nova tarefa</button>
          </div>
        </div>
      )}
    </div>
  );
}
