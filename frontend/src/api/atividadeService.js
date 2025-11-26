// src/api/atividadeService.js
import axiosClient from "./axiosClient";

/**
 * Endpoints sugeridos:
 * GET    /api/projetos/{projetoId}/atividades
 * POST   /api/projetos/{projetoId}/atividades
 * GET    /api/atividades/{id}
 * PUT    /api/atividades/{id}
 * DELETE /api/atividades/{id}
 * POST   /api/atividades/{id}/mover-cima
 * POST   /api/atividades/{id}/mover-baixo
 * PATCH  /api/atividades/{id}/status  (body: { status })
 */
const AtividadeService = {
  listByProject: (projetoId, params) =>
    axiosClient.get(`/api/projetos/${projetoId}/atividades`, { params }),

  createForProject: (projetoId, payload) =>
    axiosClient.post(`/api/projetos/${projetoId}/atividades`, payload),

  getById: (id) => axiosClient.get(`/api/atividades/${id}`),
  update: (id, payload) => axiosClient.put(`/api/atividades/${id}`, payload),
  remove: (id) => axiosClient.delete(`/api/atividades/${id}`),

  moveUp: (id) => axiosClient.post(`/api/atividades/${id}/mover-cima`),
  moveDown: (id) => axiosClient.post(`/api/atividades/${id}/mover-baixo`),

  setStatus: (id, status) =>
    axiosClient.patch(`/api/atividades/${id}/status`, { status }),
};

export default AtividadeService;
