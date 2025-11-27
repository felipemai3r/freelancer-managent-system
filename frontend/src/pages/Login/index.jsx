import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuthState, useAuthDispatch } from '../../context/AuthContext';
import Button from '../../components/ui/Button';
import Input from '../../components/ui/Input';
import styles from './Login.module.css';

const Login = () => {
  const [formData, setFormData] = useState({ email: '', senha: '' });
  const [busy, setBusy] = useState(false);
  const [errorMsg, setErrorMsg] = useState('');
  
  const { isAuthenticated, loading } = useAuthState();
  const { login } = useAuthDispatch();
  const navigate = useNavigate();

  useEffect(() => {
    if (isAuthenticated) {
      navigate('/dashboard');
    }
  }, [isAuthenticated, navigate]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrorMsg('');
    setBusy(true);

    try {
      const result = await login(formData.email, formData.senha);
      if (result.ok) {
        navigate('/dashboard');
      } else {
        setErrorMsg(result.error || 'Erro ao fazer login');
      }
    } catch (err) {
      setErrorMsg('Erro ao fazer login. Tente novamente.');
    } finally {
      setBusy(false);
    }
  };

  if (loading) {
    return null;
  }

  return (
    <div className={`${styles.page} animate__animated animate__fadeIn`}>
      <div className={styles.card}>
        <h2 className={styles.title}>Entrar no DeFreela</h2>
        <p className={styles.subtitle}>Acesse sua conta</p>

        <form onSubmit={handleSubmit} className={styles.form}>
          <div className={styles.field}>
            <label htmlFor="email">Email</label>
            <Input
              id="email"
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              placeholder="seu@email.com"
              required
              disabled={busy}
            />
          </div>

          <div className={styles.field}>
            <label htmlFor="senha">Senha</label>
            <Input
              id="senha"
              type="password"
              name="senha"
              value={formData.senha}
              onChange={handleChange}
              placeholder="Digite sua senha"
              required
              disabled={busy}
            />
          </div>

          {errorMsg && (
            <p className={styles.error}>{errorMsg}</p>
          )}

          <Button 
            type="submit" 
            variant="primary" 
            disabled={busy}
            className={styles.submitBtn}
          >
            {busy ? 'Entrando...' : 'Entrar'}
          </Button>

          <p className={styles.note}>
            ℹ️ Sistema em desenvolvimento. Use credenciais fornecidas pelo backend.
          </p>
        </form>
      </div>
    </div>
  );
};

export default Login;
