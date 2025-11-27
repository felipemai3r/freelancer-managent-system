import React from 'react';
import { Link } from 'react-router-dom';
import Button from '../../components/ui/Button';
import styles from './NotFound.module.css';

const NotFound = () => {
  return (
    <div className={`${styles.page} animate__animated animate__fadeIn`}>
      <div className={styles.content}>
        <h1 className={styles.title}>404</h1>
        <p className={styles.subtitle}>Oops! Página não encontrada</p>
        <p className={styles.text}>
          A página que você está procurando não existe ou foi movida.
        </p>
        <Link to="/">
          <Button variant="primary">Voltar para Home</Button>
        </Link>
      </div>
    </div>
  );
};

export default NotFound;
