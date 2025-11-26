import React, { useState } from "react";
import { Link, useLocation } from "react-router-dom";
import { useAuthState, useAuth } from "../../context/AuthContext";
import styles from "./Navbar.module.css";
import "animate.css";

function Navbar() {
  const location = useLocation();
  const [open, setOpen] = useState(false);
  const { isAuthenticated, user } = useAuthState();
  const { logout } = useAuth();

  const isActive = (path) =>
    location.pathname === path ? styles.activeLink : "";

  return (
    <header className={styles.header}>
      <nav className={`${styles.navbar} animate__animated animate__fadeInDown`}>
        <h1>
          <Link className={styles.logo} to="/">DeFreela</Link>
        </h1>

        <button
          aria-label="Abrir menu"
          className={styles.hambtn}
          onClick={() => setOpen(!open)}
        >
          <span className={open ? "is-active" : ""} />
          <span />
          <span />
        </button>

        <div className={`${styles.links} ${open ? styles.open : ""}`}>
          <Link onClick={() => setOpen(false)} className={`${styles.linkItem} ${isActive("/dashboard")}`} to="/dashboard">Dashboard</Link>
          <Link onClick={() => setOpen(false)} className={`${styles.linkItem} ${isActive("/empresas")}`} to="/empresas">Empresas</Link>
          <Link onClick={() => setOpen(false)} className={`${styles.linkItem} ${isActive("/freelancers")}`} to="/freelancers">Freelancers</Link>
          <Link onClick={() => setOpen(false)} className={`${styles.linkItem} ${isActive("/projetos")}`} to="/projetos">Projetos</Link>

          {isAuthenticated ? (
            <>
              <span style={{ padding: ".4rem 0", fontSize: ".9rem", color:"#e8eef7" }}>
                Olá, {user?.email || user?.id}
              </span>
              <button
                className="btn primary"
                onClick={() => {
                  logout();
                  setOpen(false);
                }}
              >
                Sair
              </button>
            </>
          ) : (
            <Link onClick={() => setOpen(false)} className={styles.linkItem} to="/login">
              Entrar
            </Link>
          )}
        </div>
      </nav>
    </header>
  );
}

export default React.memo(Navbar);
