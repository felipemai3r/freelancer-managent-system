import React, { useEffect, useState } from 'react';
import useCrud from '../../hooks/useCrud';
import empresaService from '../../api/empresaService';
import EnterpriseCard from '../../components/EnterpriseCard';
import EntityModal from '../../components/EntityModal';
import Button from '../../components/ui/Button';
import Spinner from '../../components/Spinner';
import styles from './Companies.module.css';

const Companies = () => {
  const { items, loading, error, fetchAll, create } = useCrud(empresaService);
  const [showModal, setShowModal] = useState(false);
  const [form, setForm] = useState({
    nomeFantasia: '',
    razaoSocial: '',
    cnpj: '',
    email: '',
    setor: '',
  });

  useEffect(() => {
    fetchAll();
  }, [fetchAll]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (formData) => {
    try {
      await create(formData);
      setShowModal(false);
      setForm({
        nomeFantasia: '',
        razaoSocial: '',
        cnpj: '',
        email: '',
        setor: '',
      });
      fetchAll();
    } catch (err) {
      alert('Erro ao salvar: ' + (err.message || err));
    }
  };

  const fields = [
    { name: 'nomeFantasia', label: 'Nome Fantasia', required: true },
    { name: 'razaoSocial', label: 'Razão Social', required: true },
    { name: 'cnpj', label: 'CNPJ', required: true },
    { name: 'email', label: 'Email', required: false },
    { name: 'setor', label: 'Setor', required: false },
  ];

  return (
    <div className={`${styles.page} animate__animated animate__fadeInUp`}>
      <div className={styles.header}>
        <h1 className={styles.title}>Empresas</h1>
        <div className={styles.actions}>
          <Button variant="ghost" onClick={() => fetchAll()}>
            Atualizar
          </Button>
          <Button variant="primary" onClick={() => setShowModal(true)}>
            Nova Empresa
          </Button>
        </div>
      </div>

      {loading && (
        <div className={styles.loading}>
          <Spinner size="large" />
          <p>Carregando...</p>
        </div>
      )}

      {error && !loading && (
        <p className={styles.error}>Erro ao carregar empresas.</p>
      )}

      {!loading && !error && items.length === 0 && (
        <p className={styles.empty}>Nenhuma empresa cadastrada ainda.</p>
      )}

      {!loading && !error && items.length > 0 && (
        <div className={styles.grid}>
          {items.map((empresa) => (
            <EnterpriseCard key={empresa.id} empresa={empresa} />
          ))}
        </div>
      )}

      {showModal && (
        <EntityModal
          onClose={() => setShowModal(false)}
          handleSubmit={handleSubmit}
          handleChange={handleChange}
          form={form}
          fields={fields}
        />
      )}
    </div>
  );
};

export default Companies;
