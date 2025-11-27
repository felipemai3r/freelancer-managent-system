import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import useCrud from '../../hooks/useCrud';
import empresaService from '../../api/empresaService';
import Spinner from '../../components/Spinner';
import styles from './CompanyDetail.module.css';

const CompanyDetail = () => {
  const { id } = useParams();
  const { items, loading, fetchAll } = useCrud(empresaService);
  const [empresa, setEmpresa] = useState(null);

  useEffect(() => {
    fetchAll();
  }, [fetchAll]);

  useEffect(() => {
    if (items.length > 0) {
      const found = items.find((e) => e.id === parseInt(id));
      setEmpresa(found || null);
    }
  }, [items, id]);

  if (loading) {
    return (
      <div className={styles.loading}>
        <Spinner size="large" />
        <p>Carregando...</p>
      </div>
    );
  }

  if (!empresa) {
    return (
      <div className={styles.page}>
        <p className={styles.error}>Empresa não encontrada.</p>
      </div>
    );
  }

  return (
    <div className={`${styles.page} animate__animated animate__fadeInUp`}>
      <h1 className={styles.title}>Empresa #{empresa.id}</h1>

      <div className={styles.card}>
        <div className={styles.row}>
          <strong>Nome Fantasia:</strong>
          <span>{empresa.nomeFantasia || 'Não informado'}</span>
        </div>

        <div className={styles.row}>
          <strong>Razão Social:</strong>
          <span>{empresa.razaoSocial || 'Não informado'}</span>
        </div>

        <div className={styles.row}>
          <strong>CNPJ:</strong>
          <span>{empresa.cnpj || 'Não informado'}</span>
        </div>

        <div className={styles.row}>
          <strong>Email:</strong>
          <span>{empresa.email || 'Não informado'}</span>
        </div>

        <div className={styles.row}>
          <strong>Setor:</strong>
          <span>{empresa.setor || 'Não especificado'}</span>
        </div>

        {/* O BACKEND NÃO IMPLEMENTOU PUT / DELETE */}
        {/* 
        <div className={styles.actions}>
          <button className="btn primary">Editar</button>
          <button className="btn danger">Remover</button>
        </div>
        */}
      </div>
    </div>
  );
};

export default CompanyDetail;
