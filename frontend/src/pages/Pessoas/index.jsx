import { useEffect, useState } from "react";
import PessoaService from "../../api/PessoaService";
import styles from "./Pessoas.module.css";
import "animate.css";

export default function Pessoas() {
  const [pessoas, setPessoas] = useState([]);
  const [loading, setLoading] = useState(true);

  const carregar = async () => {
    try {
      const res = await PessoaService.list();
      setPessoas(res.data);
    } catch (err) {
      console.error("Erro ao carregar pessoas:", err);
    } finally {
      setLoading(false);
    }
  };

  const handleAtivar = async (id) => {
    await PessoaService.ativar(id);
    carregar();
  };

  const handleDesativar = async (id) => {
    await PessoaService.desativar(id);
    carregar();
  };

  useEffect(() => {
    carregar();
  }, []);

  if (loading) return <p className={styles.loading}>Carregando pessoas...</p>;

  return (
    <div className={`${styles.page} animate__animated animate__fadeInUp`}>
      <h2 className={styles.title}>Pessoas</h2>

      <div className={styles.grid}>
        {pessoas.map((p) => (
          <div key={p.id} className={styles.card}>
            <p><strong>ID:</strong> {p.id}</p>
            <p><strong>Email:</strong> {p.email}</p>
            <p><strong>Tipo:</strong> {p.tipo}</p>
            <p><strong>Status:</strong> {p.ativo ? "Ativo" : "Inativo"}</p>

            <div className={styles.actions}>
              {p.ativo ? (
                <button className="btn danger" onClick={() => handleDesativar(p.id)}>
                  Desativar
                </button>
              ) : (
                <button className="btn primary" onClick={() => handleAtivar(p.id)}>
                  Ativar
                </button>
              )}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
