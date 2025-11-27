import axiosClient from './axiosClient';

const freelancerService = {
  listar: () => axiosClient.get('/api/freelancer/listar'),
  
  criar: (freelancer) => axiosClient.post('/api/freelancer/freelancer', freelancer),
  
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

export default freelancerService;