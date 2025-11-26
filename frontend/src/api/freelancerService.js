import axiosClient from "./axiosClient";

const FreelancerService = {
  listar: () => axiosClient.get("/freelancer/listar"),

  criar: (freelancer) => axiosClient.post("/freelancer/freelancer", freelancer),

  // Backend NÃO implementou:
  // buscarPorId: (id) => axiosClient.get(`/freelancer/${id}`),
  // editar: (id, data) => axiosClient.put(`/freelancer/${id}`, data),
  // remover: (id) => axiosClient.delete(`/freelancer/${id}`),
};

export default FreelancerService;
