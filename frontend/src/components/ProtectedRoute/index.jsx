// src/components/ProtectedRoute.jsx
import React from "react";
import { Navigate } from "react-router-dom";
import { useAuth, useAuthState } from "../../context/AuthContext";

export default function ProtectedRoute({ children }) {
  const { isAuthenticated } = useAuthState();

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  return children;
}