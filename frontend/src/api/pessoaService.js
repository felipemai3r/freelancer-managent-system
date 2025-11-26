import axiosClient from "./axiosClient";

const PessoaService = {
  // GET /api/pessoa/listar
  list: (params) => axiosClient.get("/api/pessoa/listar", { params }),

  // POST /api/pessoa/criarPessoa
  create: (payload) => axiosClient.post("/api/pessoa/criarPessoa", payload),

  // PUT /api/pessoa/{id}/desativar
  desativar: (id) => axiosClient.put(`/api/pessoa/${id}/desativar`),

  // PUT /api/pessoa/{id}/ativar
  ativar: (id) => axiosClient.put(`/api/pessoa/${id}/ativar`),

  // BACKEND NÃO IMPLEMENTA AINDA:
  // buscarPorId: (id) => axiosClient.get(`/api/pessoa/${id}`),
  // atualizar: (id, data) => axiosClient.put(`/api/pessoa/${id}`, data),
  // deletar: (id) => axiosClient.delete(`/api/pessoa/${id}`),
};

export default PessoaService;
