// src/axiosClient.js
import axios from "axios";

const API_BASE = process.env.REACT_APP_API_URL || "http://localhost:8080";

const axiosClient = axios.create({
  baseURL: API_BASE,
  timeout: 15000,
  headers: { "Content-Type": "application/json" },
});

// request: injeta token se existir
axiosClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) config.headers.Authorization = `Bearer ${token}`;
    return config;
  },
  (error) => Promise.reject(error)
);

// response: se 401, limpa auth e redireciona (se estiver em browser)
axiosClient.interceptors.response.use(
  (res) => res,
  (err) => {
    const status = err?.response?.status;
    if (status === 401) {
      try {
        localStorage.removeItem("token");
        localStorage.removeItem("user");
        // redirect to login if in browser (avoid during SSR)
        if (typeof window !== "undefined") window.location.href = "/login";
      } catch (e) { /* ignore */ }
    }
    return Promise.reject(err);
  }
);

export default axiosClient;
