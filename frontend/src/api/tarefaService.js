import axiosClient from "./axiosClient";

/**
 * Endpoints sugeridos:
 * GET    /api/atividades/{atividadeId}/tarefas
 * POST   /api/atividades/{atividadeId}/tarefas
 * PUT    /api/tarefas/{id}
 * DELETE /api/tarefas/{id}
 * PATCH  /api/tarefas/{id}/status  (body: { status })
 */
const TarefaService = {
  listByAtividade: (atividadeId, params) =>
    axiosClient.get(`/api/atividades/${atividadeId}/tarefas`, { params }),

  createForAtividade: (atividadeId, payload) =>
    axiosClient.post(`/api/atividades/${atividadeId}/tarefas`, payload),

  update: (id, payload) => axiosClient.put(`/api/tarefas/${id}`, payload),
  remove: (id) => axiosClient.delete(`/api/tarefas/${id}`),

  setStatus: (id, status) =>
    axiosClient.patch(`/api/tarefas/${id}/status`, { status }),
};

export default TarefaService;
