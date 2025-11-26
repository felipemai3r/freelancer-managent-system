import axiosClient from "./axiosClient";

const EntregaService = {
  list: () => axiosClient.get("/entrega"),
  getById: (id) => axiosClient.get(`/entrega/${id}`),
  create: (data) => axiosClient.post("/entrega", data),
  update: (id, data) => axiosClient.put(`/entrega/${id}`, data),
  remove: (id) => axiosClient.delete(`/entrega/${id}`),
};

export default EntregaService;
