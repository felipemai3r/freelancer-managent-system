import axiosClient from "./axiosClient";

const PessoaService = {
  // GET /pessoa/listar
  list: (params) => axiosClient.get("/pessoa/listar", { params }),

  // POST /pessoa/criarPessoa
  create: (payload) => axiosClient.post("/pessoa/criarPessoa", payload),

  // PUT /pessoa/{id}/desativar
  desativar: (id) => axiosClient.put(`/pessoa/${id}/desativar`),

  // PUT /pessoa/{id}/ativar
  ativar: (id) => axiosClient.put(`/pessoa/${id}/ativar`),

  // BACKEND NÃO IMPLEMENTA AINDA:
  // buscarPorId: (id) => axiosClient.get(`/api/pessoa/${id}`),
  // atualizar: (id, data) => axiosClient.put(`/api/pessoa/${id}`, data),
  // deletar: (id) => axiosClient.delete(`/api/pessoa/${id}`),
};

export default PessoaService;
