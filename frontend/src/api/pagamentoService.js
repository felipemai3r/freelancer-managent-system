import axiosClient from "./axiosClient";

const PagamentoService = {
  list: () => axiosClient.get("/pagamento"),
  getById: (id) => axiosClient.get(`/pagamento/${id}`),
  create: (data) => axiosClient.post("/pagamento", data),
  update: (id, data) => axiosClient.put(`/pagamento/${id}`, data),
  remove: (id) => axiosClient.delete(`/pagamento/${id}`),
};

export default PagamentoService;
