import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import useCrud from '../../hooks/useCrud';
import freelancerService from '../../api/freelancerService';
import Spinner from '../../components/Spinner';
import styles from './FreelancerDetail.module.css';

const FreelancerDetail = () => {
  const { id } = useParams();
  const { items, loading, fetchAll } = useCrud(freelancerService);
  const [freelancer, setFreelancer] = useState(null);

  useEffect(() => {
    fetchAll();
  }, [fetchAll]);

  useEffect(() => {
    if (items.length > 0) {
      const found = items.find((f) => f.id === parseInt(id));
      setFreelancer(found || null);
    }
  }, [items, id]);

  if (loading) {
    return (
      <div className={styles.loading}>
        <Spinner size="large" />
        <p>Carregando freelancer...</p>
      </div>
    );
  }

  if (!freelancer) {
    return (
      <div className={styles.page}>
        <p className={styles.error}>Freelancer não encontrado.</p>
      </div>
    );
  }

  return (
    <div className={`${styles.page} animate__animated animate__fadeInUp`}>
      <h1 className={styles.title}>Freelancer #{freelancer.id}</h1>

      <div className={styles.card}>
        <div className={styles.row}>
          <strong>Nome:</strong>
          <span>{freelancer.nome || 'Não informado'}</span>
        </div>

        <div className={styles.row}>
          <strong>Especialidade / Habilidades:</strong>
          <span>
            {freelancer.especialidade || freelancer.habilidades || 'Não especificado'}
          </span>
        </div>

        <div className={styles.row}>
          <strong>Email:</strong>
          <span>{freelancer.email || 'Não informado'}</span>
        </div>

        <div className={styles.row}>
          <strong>Portfólio:</strong>
          <span>{freelancer.portfolio || 'Não informado'}</span>
        </div>
      </div>
    </div>
  );
};

export default FreelancerDetail;
