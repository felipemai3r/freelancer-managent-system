import React from 'react';
import AppRoutes from './routes/AppRoutes';
import Navbar from './components/Navbar';
import { BrowserRouter } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';

export default function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Navbar />
        <main className="container" style={{ marginTop: "5rem" }}>
          <AppRoutes />
        </main>
      </AuthProvider>
    </BrowserRouter>
  );
}