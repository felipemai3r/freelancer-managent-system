import React from "react";
import styles from "./Home.module.css";
import "animate.css";
import { Link } from "react-router-dom";

export default function Home() {
  return (
    <div className={`${styles.page} animate__animated animate__fadeIn`}>
      <div className={styles.hero}>
        <h1 className={styles.title}>Bem-vindo ao DeFreela</h1>
        <p className={styles.subtitle}>
          Uma plataforma simples para conectar empresas, freelancers e projetos.
        </p>

        <Link to="/dashboard" className={styles.cta}>
          Ir para o Dashboard
        </Link>
      </div>
    </div>
  );
}
