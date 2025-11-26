import { Routes, Route } from "react-router-dom";
import Home from "../pages/Home";
import Dashboard from "../pages/Dashboard";
import Companies from "../pages/Companies";
import Freelancers from "../pages/Freelancers";
import Projects from "../pages/Projects";
import CompanyDetail from "../pages/CompanyDetail";
import FreelancerDetail from "../pages/FreelancerDetail";
import ProjectDetail from "../pages/ProjectDetail";
import Login from "../pages/Login";
import ProtectedRoute from "../components/ProtectedRoute";

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/" element={<Home />} />

      {/* Rotas protegidas */}
      <Route
        path="/dashboard"
        element={
          <ProtectedRoute>
            <Dashboard />
          </ProtectedRoute>
        }
      />

      <Route
        path="/empresas"
        element={
          <ProtectedRoute>
            <Companies />
          </ProtectedRoute>
        }
      />

      <Route
        path="/empresas/:id"
        element={
          <ProtectedRoute>
            <CompanyDetail />
          </ProtectedRoute>
        }
      />

      <Route
        path="/freelancers"
        element={
          <ProtectedRoute>
            <Freelancers />
          </ProtectedRoute>
        }
      />

      <Route
        path="/freelancers/:id"
        element={
          <ProtectedRoute>
            <FreelancerDetail />
          </ProtectedRoute>
        }
      />

      <Route
        path="/projetos"
        element={
          <ProtectedRoute>
            <Projects />
          </ProtectedRoute>
        }
      />

      <Route
        path="/projetos/:id"
        element={
          <ProtectedRoute>
            <ProjectDetail />
          </ProtectedRoute>
        }
      />
    </Routes>
  );
}
