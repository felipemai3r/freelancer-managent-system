import React from "react";
import styles from "./EnterpriseCard.module.css";
import "animate.css";

function EnterpriseCard({ empresa }) {
  return (
    <div className={`${styles.card} animate__animated animate__fadeInUp`}>
      <div className={styles.header}>
        <h3 className={styles.title}>
          {empresa.nomeFantasia || empresa.nome_fantasia}
        </h3>
      </div>

      <p className={styles.meta}><b>CNPJ:</b> {empresa.cnpj}</p>
      <p className={styles.desc}><b>Setor:</b> {empresa.setor || "Não informado"}</p>
    </div>
  );
}

export default React.memo(EnterpriseCard);
