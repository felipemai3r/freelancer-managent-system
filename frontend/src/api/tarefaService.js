import axiosClient from "./axiosClient";

/**
 * Endpoints sugeridos:
 * GET    /atividades/{atividadeId}/tarefas
 * POST   /atividades/{atividadeId}/tarefas
 * PUT    /tarefas/{id}
 * DELETE /tarefas/{id}
 * PATCH  /tarefas/{id}/status  (body: { status })
 */
const TarefaService = {
  listByAtividade: (atividadeId, params) =>
    axiosClient.get(`/atividades/${atividadeId}/tarefas`, { params }),

  createForAtividade: (atividadeId, payload) =>
    axiosClient.post(`/atividades/${atividadeId}/tarefas`, payload),

  update: (id, payload) => axiosClient.put(`/tarefas/${id}`, payload),
  remove: (id) => axiosClient.delete(`/tarefas/${id}`),

  setStatus: (id, status) =>
    axiosClient.patch(`/tarefas/${id}/status`, { status }),
};

export default TarefaService;
