import React, { useEffect, useState, useCallback } from "react";
import ProjetoService from "../../api/projetoService";
import ProjectCard from "../../components/ProjectCard";
import styles from "./Projects.module.css";
import "animate.css";

export default function Projects() {
  const [projects, setProjects] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchProjects = useCallback(async () => {
    setLoading(true);
    try {
      const res = await ProjetoService.list();
      setProjects(res.data);
    } catch (err) {
      setError(err.message || "Erro ao buscar projetos");
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchProjects();
  }, [fetchProjects]);

  if (loading) return <p className={styles.loading}>Carregando projetos...</p>;
  if (error) return <p className={styles.error}>Erro: {error}</p>;

  return (
    <section className={`${styles.page} animate__animated animate__fadeInUp`}>
      <h2 className={styles.title}>Projetos</h2>

      <div className={styles.grid}>
        {projects.map((p) => (
          <ProjectCard key={p.id} project={p} onOpen={(id) => console.log("Detalhe:", id)} />
        ))}
      </div>
    </section>
  );
}
