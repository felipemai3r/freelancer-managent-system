import React from "react";
import { Link } from "react-router-dom";
import styles from "./Dashboard.module.css";
import "animate.css";

export default function Dashboard() {
  return (
    <div className={`${styles.page} animate__animated animate__fadeInUp`}>
      <h2 className={styles.title}>Dashboard</h2>

      <p className={styles.subtitle}>
        Bem-vindo! Aqui você encontra um resumo rápido das seções da aplicação.
      </p>

      <div className={styles.grid}>
        <Link to="/empresas" className={`${styles.card} animate__animated animate__zoomIn`}>
          <h3>Empresas</h3>
          <p>Veja todas as empresas cadastradas no sistema.</p>
        </Link>

        <Link to="/freelancers" className={`${styles.card} animate__animated animate__zoomIn`}>
          <h3>Freelancers</h3>
          <p>Veja todos os freelancers registrados.</p>
        </Link>

        <Link to="/projetos" className={`${styles.card} animate__animated animate__zoomIn`}>
          <h3>Projetos</h3>
          <p>Acompanhe os projetos criados e seu progresso.</p>
        </Link>
      </div>
    </div>
  );
}
