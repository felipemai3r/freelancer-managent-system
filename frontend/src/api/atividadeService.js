import axiosClient from './axiosClient';

const atividadeService = {
  listarPorProjeto: async (projetoId) => {
    const response = await axiosClient.get(`/api/projetos/${projetoId}/atividades`);
    return response;
  },

  buscarPorId: async (projetoId, atividadeId) => {
    const response = await axiosClient.get(`/api/projetos/${projetoId}/atividades/${atividadeId}`);
    return response;
  },

  criar: async (projetoId, payload) => {
    const response = await axiosClient.post(`/api/projetos/${projetoId}/atividades`, payload);
    return response;
  },

  editar: async (projetoId, atividadeId, payload) => {
    const response = await axiosClient.put(`/api/projetos/${projetoId}/atividades/${atividadeId}`, payload);
    return response;
  },

  moverCima: async (projetoId, atividadeId) => {
    const response = await axiosClient.put(`/api/projetos/${projetoId}/atividades/${atividadeId}/mover-cima`);
    return response;
  },

  moverBaixo: async (projetoId, atividadeId) => {
    const response = await axiosClient.put(`/api/projetos/${projetoId}/atividades/${atividadeId}/mover-baixo`);
    return response;
  },

  remover: async (projetoId, atividadeId) => {
    const response = await axiosClient.delete(`/api/projetos/${projetoId}/atividades/${atividadeId}`);
    return response;
  },
};

export default atividadeService;