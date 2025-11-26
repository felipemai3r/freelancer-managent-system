import axiosClient from "./axiosClient";

const NotificacaoService = {
  list: () => axiosClient.get("/notificacao"),
  getById: (id) => axiosClient.get(`/notificacao/${id}`),
  create: (data) => axiosClient.post("/notificacao", data),
  update: (id, data) => axiosClient.put(`/notificacao/${id}`, data),
  remove: (id) => axiosClient.delete(`/notificacao/${id}`),
};

export default NotificacaoService;
