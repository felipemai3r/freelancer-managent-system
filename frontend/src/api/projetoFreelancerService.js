import axiosClient from "./axiosClient";

/**
 * Endpoints sugeridos:
 * GET    /api/projetos/{id}/freelancers
 * POST   /api/projetos/{id}/freelancers   (body: { freelancerId, papel, valorAcordado })
 * DELETE /api/projetos/{id}/freelancers/{freelancerId}
 */
const ProjetoFreelancerService = {
  listByProject: (projetoId) =>
    axiosClient.get(`/projetos/${projetoId}/freelancers`),

  addToProject: (projetoId, payload) =>
    axiosClient.post(`/projetos/${projetoId}/freelancers`, payload),

  removeFromProject: (projetoId, freelancerId) =>
    axiosClient.delete(`/projetos/${projetoId}/freelancers/${freelancerId}`),
};

export default ProjetoFreelancerService;
