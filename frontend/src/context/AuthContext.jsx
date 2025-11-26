
import React, { createContext, useContext, useReducer, useEffect } from "react";
import AuthService from "../api/authService";
import axiosClient from "../api/axiosClient";


const AuthStateContext = createContext(null);
const AuthDispatchContext = createContext(null);


const initialState = {
  isAuthenticated: false,
  user: null,
  token: null,
  loading: false,
  error: null,
};


function authReducer(state, action) {
  switch (action.type) {
    case "INIT_FROM_STORAGE":
      return {
        ...state,
        isAuthenticated: !!action.payload?.token,
        token: action.payload?.token || null,
        user: action.payload?.user || null,
      };
    case "LOGIN_START":
      return { ...state, loading: true, error: null };
    case "LOGIN_SUCCESS":
      return {
        ...state,
        loading: false,
        isAuthenticated: true,
        token: action.payload.token,
        user: action.payload.user,
      };
    case "LOGIN_FAILURE":
      return { ...state, loading: false, error: action.payload };
    case "LOGOUT":
      return { ...initialState };
    default:
      return state;
  }
}


export function AuthProvider({ children }) {
  const [state, dispatch] = useReducer(authReducer, initialState);


  // Inicializar do localStorage
  useEffect(() => {
    try {
      const token = localStorage.getItem("token");
      const userJson = localStorage.getItem("user");
      const user = userJson ? JSON.parse(userJson) : null;
      if (token) {
        dispatch({ type: "INIT_FROM_STORAGE", payload: { token, user } });
      }
    } catch (e) {
      console.error("Erro ao carregar auth do storage:", e);
    }
  }, []);


  const login = async (email, senha) => {
    dispatch({ type: "LOGIN_START" });
    try {
      const res = await AuthService.login({ email, senha });
      const data = res.data;
      const token = data.token;
      const user = data.user;
     
      if (!token) {
        throw new Error("Token não encontrado na resposta");
      }
     
      // Salvar no localStorage
      localStorage.setItem("token", token);
      localStorage.setItem("user", JSON.stringify(user));
     
      // Atualizar header do axios
      axiosClient.defaults.headers.common.Authorization = `Bearer ${token}`;
     
      // Atualizar estado
      dispatch({ type: "LOGIN_SUCCESS", payload: { token, user } });
     
      // ✅ REDIRECIONAR USANDO window.location (SEMPRE FUNCIONA)
      window.location.href = "/dashboard";
     
      return { ok: true };
    } catch (err) {
      const message = err.response?.data?.message || err.message || "Erro no login";
      dispatch({ type: "LOGIN_FAILURE", payload: message });
      return { ok: false, error: message };
    }
  };


  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    axiosClient.defaults.headers.common.Authorization = undefined;
    dispatch({ type: "LOGOUT" });
   
    // ✅ REDIRECIONAR USANDO window.location
    window.location.href = "/login";
  };


  return (
    <AuthStateContext.Provider value={state}>
      <AuthDispatchContext.Provider value={{ login, logout }}>
        {children}
      </AuthDispatchContext.Provider>
    </AuthStateContext.Provider>
  );
}


// Hooks
export function useAuthState() {
  const ctx = useContext(AuthStateContext);
  if (ctx === undefined) throw new Error("useAuthState must be used within AuthProvider");
  return ctx;
}


export function useAuth() {
  const ctx = useContext(AuthDispatchContext);
  if (ctx === undefined) throw new Error("useAuth must be used within AuthProvider");
  return ctx;
}

