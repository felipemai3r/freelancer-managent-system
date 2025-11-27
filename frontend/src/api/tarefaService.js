import axiosClient from './axiosClient';

const tarefaService = {
  listarPorAtividade: async (atividadeId) => {
    const response = await axiosClient.get(`/api/atividades/${atividadeId}/tarefas`);
    return response;
  },

  buscarPorId: async (atividadeId, tarefaId) => {
    const response = await axiosClient.get(`/api/atividades/${atividadeId}/tarefas/${tarefaId}`);
    return response;
  },

  criar: async (atividadeId, payload) => {
    const response = await axiosClient.post(`/api/atividades/${atividadeId}/tarefas`, payload);
    return response;
  },

  editar: async (atividadeId, tarefaId, payload) => {
    const response = await axiosClient.put(`/api/atividades/${atividadeId}/tarefas/${tarefaId}`, payload);
    return response;
  },

  atualizarStatus: async (atividadeId, tarefaId, status) => {
    const response = await axiosClient.put(`/api/atividades/${atividadeId}/tarefas/${tarefaId}`, {
      status,
    });
    return response;
  },

  remover: async (atividadeId, tarefaId) => {
    const response = await axiosClient.delete(`/api/atividades/${atividadeId}/tarefas/${tarefaId}`);
    return response;
  },
};

export default tarefaService;