import axiosClient from "./axiosClient";

const EmpresaService = {
  listar: () => axiosClient.get("/api/empresa/listar"),

  criar: (empresa) => axiosClient.post("/api/empresa/criarEmpresa", empresa),

  // Backend NÃO implementou buscar por ID, editar e deletar
  // buscarPorId: (id) => axiosClient.get(`/api/empresa/${id}`),
  // editar: (id, data) => axiosClient.put(`/api/empresa/${id}`, data),
  // remover: (id) => axiosClient.delete(`/api/empresa/${id}`),
};

export default EmpresaService;
