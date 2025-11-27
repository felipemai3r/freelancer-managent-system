import React, { useEffect, useState } from 'react';
import Button from '../../components/ui/Button';
import ActivityCard from '../../components/ActivityCard';
import EntityModal from '../../components/EntityModal';
import Spinner from '../../components/Spinner';
import useCrud from '../../hooks/useCrud';
import atividadeService from '../../api/atividadeService';
import styles from './Activities.module.css';

const Activities = () => {
  const { items, loading, fetchAll, create, update, remove } = useCrud(atividadeService);
  const [showModal, setShowModal] = useState(false);
  const [editingItem, setEditingItem] = useState(null);
  const [form, setForm] = useState({
    titulo: '',
    descricao: '',
    status: '',
    prazo: '',
  });

  useEffect(() => {
    loadActivities();
  }, []);

  const loadActivities = async () => {
    try {
      await fetchAll();
    } catch (err) {
      console.error('Erro ao carregar atividades:', err);
    }
  };

  const handleOpenModal = (activity = null) => {
    if (activity) {
      setEditingItem(activity);
      setForm({
        titulo: activity.titulo || '',
        descricao: activity.descricao || '',
        status: activity.status || '',
        prazo: activity.prazo || '',
      });
    } else {
      setEditingItem(null);
      setForm({ titulo: '', descricao: '', status: '', prazo: '' });
    }
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setEditingItem(null);
    setForm({ titulo: '', descricao: '', status: '', prazo: '' });
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (formData) => {
    try {
      if (editingItem) {
        await update(editingItem.id, formData);
      } else {
        await create(formData);
      }
      handleCloseModal();
    } catch (err) {
      console.error('Erro ao salvar atividade:', err);
      alert('Erro ao salvar atividade');
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Tem certeza que deseja remover esta atividade?')) {
      try {
        await remove(id);
      } catch (err) {
        console.error('Erro ao remover atividade:', err);
        alert('Erro ao remover atividade');
      }
    }
  };

  const fields = [
    { name: 'titulo', label: 'Título', required: true, placeholder: 'Nome da atividade' },
    { name: 'descricao', label: 'Descrição', required: true, placeholder: 'Descrição da atividade' },
    { name: 'status', label: 'Status', required: true, placeholder: 'Ex: Em andamento, Concluído' },
    { name: 'prazo', label: 'Prazo', required: false, placeholder: 'Data de entrega' },
  ];

  if (loading) return <Spinner />;

  return (
    <div className={`${styles.page} animate__animated animate__fadeInUp`}>
      <div className={styles.header}>
        <h1 className={styles.title}>Atividades</h1>
        <Button variant="primary" onClick={() => handleOpenModal()}>
          + Registrar Atividade
        </Button>
      </div>

      {items.length === 0 ? (
        <p className={styles.empty}>Nenhuma atividade cadastrada ainda.</p>
      ) : (
        <div className={styles.grid}>
          {items.map((activity) => (
            <ActivityCard
              key={activity.id}
              activity={activity}
              onEdit={() => handleOpenModal(activity)}
              onDelete={() => handleDelete(activity.id)}
            />
          ))}
        </div>
      )}

      {showModal && (
        <EntityModal
          onClose={handleCloseModal}
          handleSubmit={handleSubmit}
          handleChange={handleChange}
          form={form}
          fields={fields}
        />
      )}
    </div>
  );
};

export default Activities;
