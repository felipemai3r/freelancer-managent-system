import axiosClient from "./axiosClient";

const FreelancerService = {
  listar: () => axiosClient.get("/api/freelancer/freelancers"),

  criar: (freelancer) => axiosClient.post("/api/freelancer/freelancer", freelancer),

  // Backend NÃO implementou:
  // buscarPorId: (id) => axiosClient.get(`/api/freelancer/${id}`),
  // editar: (id, data) => axiosClient.put(`/api/freelancer/${id}`, data),
  // remover: (id) => axiosClient.delete(`/api/freelancer/${id}`),
};

export default FreelancerService;
