import React, { useEffect, useState, useCallback } from "react";
import FreelancerService from "../../api/freelancerService";
import FreelancerCard from "../../components/FreelancerCard";
import styles from "./Freelancers.module.css";
import "animate.css";

export default function Freelancers() {
  const [freelancers, setFreelancers] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchFreelancers = useCallback(async () => {
    try {
      const res = await FreelancerService.listar();
      setFreelancers(res.data);
    } catch (err) {
      console.error("Erro ao carregar freelancers:", err);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchFreelancers();
  }, [fetchFreelancers]);

  if (loading) return <p className={styles.loading}>Carregando freelancers...</p>;

  return (
    <div className={`${styles.page} animate__animated animate__fadeInUp`}>
      <h2 className={styles.title}>Freelancers</h2>

      <div className={styles.grid}>
        {freelancers.map((freela) => (
          <FreelancerCard key={freela.id} freela={freela} />
        ))}
      </div>
    </div>
  );
}
