import axiosClient from './axiosClient';

const empresaService = {
  listar: () => axiosClient.get('/api/empresa/listar'),
  
  criar: (empresa) => axiosClient.post('/api/empresa/criarEmpresa', empresa),
  
  buscarPorId: (id) => {
    console.warn('Endpoint buscarPorId não implementado no backend');
    return Promise.reject('Não implementado');
  },
  
  editar: (id, data) => {
    console.warn('Endpoint editar não implementado no backend');
    return Promise.reject('Não implementado');
  },
  
  remover: (id) => {
    console.warn('Endpoint remover não implementado no backend');
    return Promise.reject('Não implementado');
  },
};

export default empresaService;