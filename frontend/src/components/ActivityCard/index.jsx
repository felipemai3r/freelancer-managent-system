import React from 'react';
import Button from '../ui/Button';
import styles from './ActivityCard.module.css';

const ActivityCard = ({ activity, onEdit, onDelete }) => {
  return (
    <div className={`${styles.card} animate__animated animate__fadeIn`}>
      <div className={styles.header}>
        <h3 className={styles.title}>{activity.titulo}</h3>
        <span className={styles.status}>{activity.status}</span>
      </div>

      <div className={styles.body}>
        <p className={styles.description}>{activity.descricao}</p>
        {activity.prazo && (
          <p className={styles.prazo}>📅 Prazo: {activity.prazo}</p>
        )}
      </div>

      <div className={styles.actions}>
        <Button variant="primary" onClick={onEdit}>
          Editar
        </Button>
        <Button variant="ghost" onClick={onDelete}>
          Apagar
        </Button>
      </div>
    </div>
  );
};

export default ActivityCard;
