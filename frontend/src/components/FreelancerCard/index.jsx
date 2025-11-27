import React from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './FreelancerCard.module.css';

const FreelancerCard = ({ freelancer }) => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(`/freelancers/${freelancer.id}`);
  };

  return (
    <div 
      className={`${styles.card} animate__animated animate__fadeInUp`}
      onClick={handleClick}
    >
      <h3 className={styles.name}>{freelancer.nome || 'Sem nome'}</h3>
      
      <div className={styles.badge}>
        {freelancer.especialidade || freelancer.habilidades || 'Sem especialidade'}
      </div>

      {freelancer.portfolio && (
        <p className={styles.portfolio}>
          <strong>Portfólio:</strong> {freelancer.portfolio}
        </p>
      )}
    </div>
  );
};

export default FreelancerCard;
