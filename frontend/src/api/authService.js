// src/api/authService.js
import axiosClient from "./axiosClient";

/**
 * AuthService expects backend at POST /api/auth/login
 * Body: { email, senha }
 * Response (format A - assumed): { token: "...", user: { id, email, tipoUsuario } }
 *
 * If backend returns a different shape, adapt this file accordingly.
 */
const AuthService = {
  login: (credentials) => axiosClient.post("/auth/login", credentials),
  // optional endpoints for the future:
  // refresh: () => axiosClient.post('/api/auth/refresh'),
  // logout: () => axiosClient.post('/api/auth/logout'),
};

export default AuthService;
