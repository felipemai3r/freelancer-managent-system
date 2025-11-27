import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuthState } from '../../context/AuthContext';
import Spinner from '../Spinner';

const ProtectedRoute = ({ children }) => {
  const { isAuthenticated, loading } = useAuthState();

  if (loading) {
    return (
      <div style={{ 
        display: 'flex', 
        justifyContent: 'center', 
        alignItems: 'center', 
        minHeight: '100vh' 
      }}>
        <Spinner size="large" />
      </div>
    );
  }

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  return children;
};

export default ProtectedRoute;
