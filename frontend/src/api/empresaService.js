import axiosClient from "./axiosClient";

const EmpresaService = {
  listar: () => axiosClient.get("/empresa/listar"),

  criar: (empresa) => axiosClient.post("/empresa/criarEmpresa", empresa),

  // Backend NÃO implementou buscar por ID, editar e deletar
  // buscarPorId: (id) => axiosClient.get(`/empresa/${id}`),
  // editar: (id, data) => axiosClient.put(`/empresa/${id}`, data),
  // remover: (id) => axiosClient.delete(`/empresa/${id}`),
};

export default EmpresaService;
