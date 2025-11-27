import axiosClient from './axiosClient';

const pessoaService = {
  listar: () => axiosClient.get('/api/pessoas'),
  buscarPorId: (id) => axiosClient.get(`/api/pessoas/${id}`),
  listarAtivas: () => axiosClient.get('/api/pessoas/ativas'),
  criar: (pessoa) => axiosClient.post('/api/pessoas', pessoa),
  editar: (id, data) => axiosClient.put(`/api/pessoas/${id}`, data),
  desativar: (id) => axiosClient.put(`/api/pessoas/${id}/desativar`),
  ativar: (id) => axiosClient.put(`/api/pessoas/${id}/ativar`),
};

export default pessoaService;
