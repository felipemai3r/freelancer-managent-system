import React from "react";
import styles from "./TaskItem.module.css";

export default function TaskItem({ tarefa, onToggle }) {
  const done = tarefa.status === "CONCLUIDA" || tarefa.isConcluida;

  return (
    <div className={styles.item}>
      <label className={styles.left}>
        <input
          type="checkbox"
          checked={!!done}
          onChange={() => onToggle && onToggle(tarefa)}
        />
        <div className={styles.info}>
          <div className={styles.title}>{tarefa.titulo}</div>
          <div className={styles.meta}>
            {tarefa.prioridade ? `Prioridade: ${tarefa.prioridade}` : ""}
            {tarefa.prazo ? ` • Prazo: ${tarefa.prazo}` : ""}
          </div>
        </div>
      </label>

      <div className={styles.actions}>
        <span className={styles.badge}>{tarefa.status}</span>
      </div>
    </div>
  );
}
