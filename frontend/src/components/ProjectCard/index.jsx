import React from 'react';
import { useNavigate } from 'react-router-dom';
import Button from '../ui/Button';
import styles from './ProjectCard.module.css';

const ProjectCard = ({ projeto }) => {
  const navigate = useNavigate();

  const handleView = () => {
    navigate(`/projetos/${projeto.id}`);
  };

  return (
    <div className={`${styles.card} animate__animated animate__fadeInUp`}>
      <h3 className={styles.title}>{projeto.titulo || 'Sem título'}</h3>
      
      {projeto.nomeEmpresa && (
        <p className={styles.company}>
          <strong>Empresa:</strong> {projeto.nomeEmpresa}
        </p>
      )}

      {projeto.descricao && (
        <p className={styles.description}>{projeto.descricao}</p>
      )}

      {projeto.status && (
        <div className={styles.status}>
          Status: <span className={styles.statusBadge}>{projeto.status}</span>
        </div>
      )}

      <div className={styles.actions}>
        <Button variant="primary" onClick={handleView}>
          Ver
        </Button>
      </div>
    </div>
  );
};

export default ProjectCard;