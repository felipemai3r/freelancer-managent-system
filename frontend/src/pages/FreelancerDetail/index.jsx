import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import FreelancerService from "../../api/freelancerService";
import styles from "./FreelancerDetail.module.css";
import "animate.css";

export default function FreelancerDetail() {
  const { id } = useParams();
  const [freela, setFreela] = useState(null);

  useEffect(() => {
    FreelancerService.list().then((res) => {
      const found = res.data.find((f) => String(f.id) === String(id));
      setFreela(found);
    });
  }, [id]);

  if (!freela) return <p className={styles.loading}>Carregando freelancer...</p>;

  return (
    <div className={`${styles.page} animate__animated animate__fadeInUp`}>
      <h2 className={styles.title}>Freelancer #{freela.id}</h2>

      <div className={styles.card}>
        <p><strong>Nome:</strong> {freela.nome || freela.nomeCompleto}</p>
        <p><strong>Especialidade:</strong> {freela.especialidade || freela.habilidades}</p>
        <p><strong>Email:</strong> {freela.email}</p>
        <p><strong>Portfólio:</strong> {freela.portfolioUrl || "Não informado"}</p>
      </div>

      {/* comentários futuros */}
      {/* <button className="btn">Editar</button> */}
      {/* <button className="btn danger">Excluir</button> */}
    </div>
  );
}
