import React from "react";
import styles from "./ProjectCard.module.css";
import "animate.css";

function ProjectCard({ project, onOpen }) {
  return (
    <article className={`${styles.card} animate__animated animate__fadeInUp`}>
      <h3 className={styles.title}>{project.titulo}</h3>

      <p className={styles.meta}>
        {project.nomeEmpresa || project.nome_empresa}
      </p>

      <p className={styles.desc}>{project.descricao}</p>

      <div className={styles.footer}>
        <span>Status: {project.status}</span>
        <button className="btn primary" onClick={() => onOpen(project.id)}>
          Ver
        </button>
      </div>
    </article>
  );
}

export default React.memo(ProjectCard);
