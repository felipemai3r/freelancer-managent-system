// src/projetoService.js
import axiosClient from "./axiosClient";

/**
 * Endpoints sugeridos (backend pode ajustar):
 * GET    /projetos
 * GET    /projetos/{id}
 * POST   /projetos
 * PUT    /projetos/{id}
 * DELETE /projetos/{id}
 */
const ProjetoService = {
  list: (params) => axiosClient.get("/projetos", { params }),
  getById: (id) => axiosClient.get(`/projetos/${id}`),
  create: (payload) => axiosClient.post("/projetos", payload),
  update: (id, payload) => axiosClient.put(`/projetos/${id}`, payload),
  remove: (id) => axiosClient.delete(`/projetos/${id}`),

  // helpers
  listByEmpresa: (empresaId, params) =>
    axiosClient.get(`/empresas/${empresaId}/projetos`, { params }),
};

export default ProjetoService;
