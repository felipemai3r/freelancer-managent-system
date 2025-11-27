import React from 'react';
import { useNavigate } from 'react-router-dom';
import Button from '../../components/ui/Button';
import styles from './Home.module.css';

const Home = () => {
  const navigate = useNavigate();

  return (
    <div className={`${styles.page} animate__animated animate__fadeIn`}>
      <div className={styles.hero}>
        <h1 className={styles.title}>Bem-vindo ao DeFreela</h1>
        <p className={styles.subtitle}>
          A plataforma completa para gerenciar empresas, freelancers e projetos.
          Conecte talentos, organize tarefas e acompanhe entregas em um só lugar.
        </p>
        <Button variant="primary" onClick={() => navigate('/dashboard')}>
          Ir para o Dashboard
        </Button>
      </div>
    </div>
  );
};

export default Home;
