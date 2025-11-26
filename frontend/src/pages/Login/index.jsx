import React, { useState } from "react";
import { useAuth } from "../../context/AuthContext";
import styles from "./Login.module.css";
import "animate.css";

export default function Login() {
  const { login } = useAuth();
  const [form, setForm] = useState({ email: "", senha: "" });
  const [busy, setBusy] = useState(false);
  const [error, setError] = useState(null);

  const onChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setBusy(true);
    setError(null);

    const res = await login(form.email, form.senha);

    if (!res.ok) {
      setError(res.error || "Erro ao autenticar");
    }

    setBusy(false);
  };

  return (
    <div className={`${styles.page} animate__animated animate__fadeIn`}>
      <div className={`${styles.card} card`}>
        <h2>Entrar</h2>
        <p className={styles.muted}>Use seu email e senha para acessar</p>

        <form onSubmit={handleSubmit} className={styles.form}>
          <label>
            <span>Email</span>
            <input
              name="email"
              type="email"
              value={form.email}
              onChange={onChange}
              placeholder="seu@exemplo.com"
              required
            />
          </label>

          <label>
            <span>Senha</span>
            <input
              name="senha"
              type="password"
              value={form.senha}
              onChange={onChange}
              placeholder="••••••••"
              required
            />
          </label>

          {error && <div className={styles.error}>{error}</div>}

          <button className="btn primary" type="submit" disabled={busy}>
            {busy ? "Entrando..." : "Entrar"}
          </button>
        </form>

        <small className={styles.info}>
          Backend ainda sem /api/auth/login?  
          O sistema permanece em modo desenvolvimento.
        </small>
      </div>
    </div>
  );
}
