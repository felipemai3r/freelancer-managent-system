import React, { useEffect, useState } from 'react';
import ProjectCard from '../../components/ProjectCard';
import Spinner from '../../components/Spinner';
import projetoService from '../../api/projetoService';
import styles from './Projects.module.css';

const Projects = () => {
  const [projects, setProjects] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    loadProjects();
  }, []);

  const loadProjects = async () => {
    try {
      setLoading(true);
      const response = await projetoService.listar();
      setProjects(response.data);
    } catch (err) {
      console.error('Erro ao carregar projetos:', err);
      setError('Erro ao carregar projetos');
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className={styles.page}>
        <Spinner />
      </div>
    );
  }

  return (
    <div className={`${styles.page} animate__animated animate__fadeInUp`}>
      <h1 className={styles.title}>Projetos</h1>

      {error && (
        <div className={styles.error}>
          {error}
        </div>
      )}

      {!error && projects.length === 0 ? (
        <p className={styles.empty}>Nenhum projeto cadastrado ainda.</p>
      ) : (
        <div className={styles.grid}>
          {projects.map((projeto) => (
            <ProjectCard key={projeto.id} projeto={projeto} />
          ))}
        </div>
      )}
    </div>
  );
};

export default Projects;