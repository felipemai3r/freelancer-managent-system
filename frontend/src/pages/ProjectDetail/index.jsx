import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Button from '../../components/ui/Button';
import ActivityCard from '../../components/ActivityCard';
import EntityModal from '../../components/EntityModal';
import Spinner from '../../components/Spinner';
import projetoService from '../../api/projetoService';
import atividadeService from '../../api/atividadeService';
import styles from './ProjectDetail.module.css';

const ProjectDetail = () => {
  const { id } = useParams();
  const [projeto, setProjeto] = useState(null);
  const [atividades, setAtividades] = useState([]); // ✅ CORRIGIDO: Array vazio
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editingItem, setEditingItem] = useState(null);
  const [form, setForm] = useState({
    nome: '',
    descricao: '',
  });

  useEffect(() => {
    loadProjectDetails();
  }, [id]);

  const loadProjectDetails = async () => {
    try {
      setLoading(true);
      
      // Buscar dados do projeto
      const projetoResponse = await projetoService.buscarPorId(id);
      setProjeto(projetoResponse.data);

      // Buscar atividades do projeto
      const atividadesResponse = await atividadeService.listarPorProjeto(id);
      // ✅ CORRIGIDO: Garantir que seja array
      setAtividades(Array.isArray(atividadesResponse.data) ? atividadesResponse.data : []);
    } catch (err) {
      console.error('Erro ao carregar projeto:', err);
      setAtividades([]); // ✅ CORRIGIDO: Setar array vazio em caso de erro
    } finally {
      setLoading(false);
    }
  };

  const handleOpenModal = (activity = null) => {
    if (activity) {
      setEditingItem(activity);
      setForm({
        nome: activity.nome || '',
        descricao: activity.descricao || '',
      });
    } else {
      setEditingItem(null);
      setForm({ nome: '', descricao: '' });
    }
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setEditingItem(null);
    setForm({ nome: '', descricao: '' });
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (formData) => {
    try {
      if (editingItem) {
        await atividadeService.editar(id, editingItem.id, formData);
      } else {
        await atividadeService.criar(id, formData);
      }
      handleCloseModal();
      loadProjectDetails();
    } catch (err) {
      console.error('Erro ao salvar atividade:', err);
      alert('Erro ao salvar atividade');
    }
  };

  const handleDelete = async (atividadeId) => {
    if (window.confirm('Tem certeza que deseja remover esta atividade?')) {
      try {
        await atividadeService.remover(id, atividadeId);
        loadProjectDetails();
      } catch (err) {
        console.error('Erro ao remover atividade:', err);
        alert('Erro ao remover atividade');
      }
    }
  };

  const handleMoveUp = async (atividadeId) => {
    try {
      await atividadeService.moverCima(id, atividadeId);
      loadProjectDetails();
    } catch (err) {
      console.error('Erro ao mover atividade:', err);
    }
  };

  const handleMoveDown = async (atividadeId) => {
    try {
      await atividadeService.moverBaixo(id, atividadeId);
      loadProjectDetails();
    } catch (err) {
      console.error('Erro ao mover atividade:', err);
    }
  };

  const fields = [
    { name: 'nome', label: 'Nome', required: true, placeholder: 'Nome da atividade' },
    { name: 'descricao', label: 'Descrição', required: true, placeholder: 'Descrição da atividade' },
  ];

  if (loading) {
    return (
      <div className={styles.page}>
        <Spinner />
      </div>
    );
  }

  if (!projeto) {
    return (
      <div className={`${styles.page} animate__animated animate__fadeInUp`}>
        <h1 className={styles.title}>Projeto não encontrado</h1>
      </div>
    );
  }

  return (
    <div className={`${styles.page} animate__animated animate__fadeInUp`}>
      <div className={styles.projectHeader}>
        <div>
          <h1 className={styles.title}>{projeto.titulo}</h1>
          <p className={styles.description}>{projeto.descricao}</p>
          <div className={styles.projectMeta}>
            <span className={`${styles.badge} ${styles[`status-${projeto.status?.toLowerCase()}`]}`}>
              {projeto.status}
            </span>
            {projeto.dataInicio && (
              <span className={styles.metaText}>
                Início: {new Date(projeto.dataInicio).toLocaleDateString('pt-BR')}
              </span>
            )}
            {projeto.dataFimPrevista && (
              <span className={styles.metaText}>
                Fim: {new Date(projeto.dataFimPrevista).toLocaleDateString('pt-BR')}
              </span>
            )}
          </div>
        </div>
      </div>

      <div className={styles.section}>
        <div className={styles.sectionHeader}>
          <h2 className={styles.sectionTitle}>Atividades do Projeto</h2>
          <Button variant="primary" onClick={() => handleOpenModal()}>
            + Adicionar Atividade
          </Button>
        </div>

        {atividades.length === 0 ? (
          <div className={styles.emptyState}>
            <p className={styles.emptyText}>Nenhuma atividade cadastrada ainda.</p>
          </div>
        ) : (
          <div className={styles.activitiesList}>
            {atividades.map((activity, index) => (
              <div key={activity.id} className={styles.activityWrapper}>
                <div className={styles.activityOrder}>#{activity.ordem}</div>
                <div className={styles.activityContent}>
                  <ActivityCard
                    activity={activity}
                    onEdit={() => handleOpenModal(activity)}
                    onDelete={() => handleDelete(activity.id)}
                  />
                </div>
                <div className={styles.activityActions}>
                  <button
                    className={styles.moveButton}
                    onClick={() => handleMoveUp(activity.id)}
                    disabled={index === 0}
                    title="Mover para cima"
                  >
                    ↑
                  </button>
                  <button
                    className={styles.moveButton}
                    onClick={() => handleMoveDown(activity.id)}
                    disabled={index === atividades.length - 1}
                    title="Mover para baixo"
                  >
                    ↓
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>

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

export default ProjectDetail;