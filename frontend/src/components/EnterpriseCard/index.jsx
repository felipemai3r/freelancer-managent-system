import React from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './EnterpriseCard.module.css';

const EnterpriseCard = ({ empresa }) => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(`/empresas/${empresa.id}`);
  };

  return (
    <div 
      className={`${styles.card} animate__animated animate__fadeInUp`}
      onClick={handleClick}
    >
      <h3 className={styles.name}>{empresa.nomeFantasia || 'Sem nome'}</h3>
      <p className={styles.info}>
        <strong>CNPJ:</strong> {empresa.cnpj || 'Não informado'}
      </p>
      <p className={styles.info}>
        <strong>Setor:</strong> {empresa.setor || 'Não especificado'}
      </p>
    </div>
  );
};

export default EnterpriseCard;
