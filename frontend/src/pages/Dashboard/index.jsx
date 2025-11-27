import React from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './Dashboard.module.css';

const Dashboard = () => {
  const navigate = useNavigate();

  const cards = [
    {
      title: 'Empresas',
      description: 'Gerencie empresas cadastradas, crie novas e acompanhe informações.',
      path: '/empresas',
    },
    {
      title: 'Freelancers',
      description: 'Visualize freelancers disponíveis, especialidades e portfólios.',
      path: '/freelancers',
    },
    {
      title: 'Projetos',
      description: 'Acompanhe projetos em andamento, status e entregas.',
      path: '/projetos',
    },
  ];

  return (
    <div className={`${styles.page} animate__animated animate__fadeInUp`}>
      <h1 className={styles.title}>Dashboard</h1>
      <p className={styles.subtitle}>Bem-vindo! Escolha uma seção para começar.</p>

      <div className={styles.grid}>
        {cards.map((card, index) => (
          <div
            key={card.path}
            className={`${styles.card} animate__animated animate__zoomIn`}
            style={{ animationDelay: `${index * 0.1}s` }}
            onClick={() => navigate(card.path)}
          >
            <h3 className={styles.cardTitle}>{card.title}</h3>
            <p className={styles.cardDesc}>{card.description}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Dashboard;
