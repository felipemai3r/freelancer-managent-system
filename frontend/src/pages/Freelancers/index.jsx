import React, { useEffect } from 'react';
import useCrud from '../../hooks/useCrud';
import freelancerService from '../../api/freelancerService';
import FreelancerCard from '../../components/FreelancerCard';
import Spinner from '../../components/Spinner';
import styles from './Freelancers.module.css';

const Freelancers = () => {
  const { items, loading, error, fetchAll } = useCrud(freelancerService);

  useEffect(() => {
    fetchAll();
  }, [fetchAll]);

  return (
    <div className={`${styles.page} animate__animated animate__fadeInUp`}>
      <h1 className={styles.title}>Freelancers</h1>

      {loading && (
        <div className={styles.loading}>
          <Spinner size="large" />
          <p>Carregando freelancers...</p>
        </div>
      )}

      {error && !loading && (
        <p className={styles.error}>Erro ao carregar freelancers.</p>
      )}

      {!loading && !error && items.length === 0 && (
        <p className={styles.empty}>Nenhum freelancer cadastrado ainda.</p>
      )}

      {!loading && !error && items.length > 0 && (
        <div className={styles.grid}>
          {items.map((freelancer) => (
            <FreelancerCard key={freelancer.id} freelancer={freelancer} />
          ))}
        </div>
      )}
    </div>
  );
};

export default Freelancers;
