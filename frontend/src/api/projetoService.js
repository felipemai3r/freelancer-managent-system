import axiosClient from './axiosClient';

const projetoService = {
  listar: () => axiosClient.get('/api/projetos'),
  buscarPorId: (id) => axiosClient.get(`/api/projetos/${id}`),
  criar: (projeto) => axiosClient.post('/api/projetos', projeto),
  editar: (id, data) => axiosClient.put(`/api/projetos/${id}`, data),
  remover: (id) => axiosClient.delete(`/api/projetos/${id}`),
  listarPorEmpresa: (empresaId) => axiosClient.get(`/api/empresas/${empresaId}/projetos`),
  adicionarFreelancer: (projetoId, data) => axiosClient.post(`/api/projetos/${projetoId}/freelancers`, data),
  listarFreelancers: (projetoId) => axiosClient.get(`/api/projetos/${projetoId}/freelancers`),
  removerFreelancer: (projetoId, freelancerId) => axiosClient.delete(`/api/projetos/${projetoId}/freelancers/${freelancerId}`),
};

export default projetoService;
