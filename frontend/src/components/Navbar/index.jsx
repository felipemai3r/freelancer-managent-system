import React, { useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { useAuthState, useAuthDispatch } from '../../context/AuthContext';
import styles from './Navbar.module.css';

const Navbar = () => {
  const [menuOpen, setMenuOpen] = useState(false);
  const location = useLocation();
  const { isAuthenticated, user } = useAuthState();
  const { logout } = useAuthDispatch();

  const toggleMenu = () => setMenuOpen(!menuOpen);
  const closeMenu = () => setMenuOpen(false);

  const handleLogout = () => {
    logout();
    closeMenu();
  };

  const isActive = (path) => location.pathname === path;

  return (
    <header className={`${styles.navbar} animate__animated animate__fadeInDown`}>
      <div className={styles.container}>
        <Link to="/" className={styles.logo} onClick={closeMenu}>
          De<span>Freela</span>
        </Link>

        <button 
          className={styles.hamburger} 
          onClick={toggleMenu}
          aria-label="Toggle menu"
        >
          <span></span>
          <span></span>
          <span></span>
        </button>

        <nav className={`${styles.nav} ${menuOpen ? styles.open : ''}`}>
          {isAuthenticated && (
            <>
              <Link 
                to="/dashboard" 
                className={isActive('/dashboard') ? styles.active : ''}
                onClick={closeMenu}
              >
                Dashboard
              </Link>
              <Link 
                to="/empresas" 
                className={isActive('/empresas') ? styles.active : ''}
                onClick={closeMenu}
              >
                Empresas
              </Link>
              <Link 
                to="/freelancers" 
                className={isActive('/freelancers') ? styles.active : ''}
                onClick={closeMenu}
              >
                Freelancers
              </Link>
              <Link 
                to="/projetos" 
                className={isActive('/projetos') ? styles.active : ''}
                onClick={closeMenu}
              >
                Projetos
              </Link>
              <Link 
                to="/atividades" 
                className={isActive('/atividades') ? styles.active : ''}
                onClick={closeMenu}
              >
                Atividades
              </Link>
            </>
          )}

          <div className={styles.auth}>
            {isAuthenticated ? (
              <>
                <span className={styles.userEmail}>Olá, {user?.email}</span>
                <button className={`btn ghost ${styles.logoutBtn}`} onClick={handleLogout}>
                  Sair
                </button>
              </>
            ) : (
              <Link to="/login" onClick={closeMenu}>
                <button className="btn primary">Entrar</button>
              </Link>
            )}
          </div>
        </nav>
      </div>
    </header>
  );
};

export default Navbar;
