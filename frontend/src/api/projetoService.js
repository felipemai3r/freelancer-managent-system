// src/api/projetoService.js
import axiosClient from "./axiosClient";

/**
 * Endpoints sugeridos (backend pode ajustar):
 * GET    /api/projetos
 * GET    /api/projetos/{id}
 * POST   /api/projetos
 * PUT    /api/projetos/{id}
 * DELETE /api/projetos/{id}
 */
const ProjetoService = {
  list: (params) => axiosClient.get("/api/projetos", { params }),
  getById: (id) => axiosClient.get(`/api/projetos/${id}`),
  create: (payload) => axiosClient.post("/api/projetos", payload),
  update: (id, payload) => axiosClient.put(`/api/projetos/${id}`, payload),
  remove: (id) => axiosClient.delete(`/api/projetos/${id}`),

  // helpers
  listByEmpresa: (empresaId, params) =>
    axiosClient.get(`/api/empresas/${empresaId}/projetos`, { params }),
};

export default ProjetoService;
