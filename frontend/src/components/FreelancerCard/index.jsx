import React from "react";
import styles from "./FreelancerCard.module.css";
import "animate.css";

function FreelancerCard({ freela }) {
  return (
    <div className={`${styles.card} animate__animated animate__fadeInUp`}>
      <div className={styles.row}>
        <h3 className={styles.name}>{freela.nome}</h3>
        <span className={styles.badge}>
          {freela.especialidade || "Sem especialidade"}
        </span>
      </div>

      <p className={styles.skills}>
        <b>Portfólio:</b> {freela.portfolioUrl || "Nenhum"}
      </p>
    </div>
  );
}

export default React.memo(FreelancerCard);
