import React, { createContext, useContext, useState, useEffect } from 'react';
import authService from '../api/authService';
import axiosClient from '../api/axiosClient';

const AuthStateContext = createContext();
const AuthDispatchContext = createContext();

export const AuthProvider = ({ children }) => {
  const [state, setState] = useState({
    isAuthenticated: false,
    user: null,
    token: null,
    loading: true,
    error: null,
  });

  useEffect(() => {
    const token = localStorage.getItem('token');
    const userStr = localStorage.getItem('user');
    
    if (token && userStr) {
      try {
        const user = JSON.parse(userStr);
        setState({
          isAuthenticated: true,
          user,
          token,
          loading: false,
          error: null,
        });
      } catch (err) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        setState((prev) => ({ ...prev, loading: false }));
      }
    } else {
      setState((prev) => ({ ...prev, loading: false }));
    }
  }, []);

  const login = async (email, senha) => {
    try {
      setState((prev) => ({ ...prev, loading: true, error: null }));
      
      const data = await authService.login(email, senha);
      
      // API retorna: { token, tipo: "Bearer", pessoa: {...} }
      const { token, pessoa } = data;
      
      localStorage.setItem('token', token);
      localStorage.setItem('user', JSON.stringify(pessoa));
      
      axiosClient.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      
      setState({
        isAuthenticated: true,
        user: pessoa,
        token,
        loading: false,
        error: null,
      });
      
      return { ok: true };
    } catch (error) {
      const errorMsg = error.response?.data?.message || error.message || 'Erro ao fazer login';
      setState((prev) => ({
        ...prev,
        loading: false,
        error: errorMsg,
      }));
      return { ok: false, error: errorMsg };
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    delete axiosClient.defaults.headers.common['Authorization'];
    
    setState({
      isAuthenticated: false,
      user: null,
      token: null,
      loading: false,
      error: null,
    });
  };

  return (
    <AuthStateContext.Provider value={state}>
      <AuthDispatchContext.Provider value={{ login, logout }}>
        {children}
      </AuthDispatchContext.Provider>
    </AuthStateContext.Provider>
  );
};

export const useAuthState = () => {
  const context = useContext(AuthStateContext);
  if (!context) {
    throw new Error('useAuthState must be used within AuthProvider');
  }
  return context;
};

export const useAuthDispatch = () => {
  const context = useContext(AuthDispatchContext);
  if (!context) {
    throw new Error('useAuthDispatch must be used within AuthProvider');
  }
  return context;
};
