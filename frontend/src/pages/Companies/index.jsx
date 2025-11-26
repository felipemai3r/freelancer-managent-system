import React, { useEffect, useState } from "react";
import EmpresaService from "../../api/empresaService";
import useCrud from "../../hooks/useCrud";
import EnterpriseCard from "../../components/EnterpriseCard";
import EntityModal from "../../components/EntityModal";

import styles from "./Companies.module.css";
import "animate.css";

const fields = [
  { name: "nomeEmpresa", label: "Nome da Empresa", required: true, placeholder: "Nome da empresa" },
  { name: "cnpj", label: "CNPJ", required: true, placeholder: "00.000.000/0000-00" },
  { name: "telefone", label: "Telefone", required: false, placeholder: "(00) 00000-0000" },
  { name: "endereco", label: "Endereço", required: false, placeholder: "Endereço completo" },
];

export default function Companies() {
  const { items, loading, error, fetchAll, create } = useCrud(EmpresaService);
  const [modalOpen, setModalOpen] = useState(false);

  useEffect(() => { fetchAll(); }, [fetchAll]);

  const handleSubmit = async (data) => {
    try {
      await create(data);
      setModalOpen(false);
      fetchAll(); // Recarrega a lista
    } catch (err) {
      console.error("Erro ao salvar empresa", err);
      alert("Erro ao salvar: " + (err.message || err));
    }
  };

  return (
    <div className={`${styles.container} animate__animated animate__fadeInUp`}>
      <div className={styles.headerRow}>
        <h2 className={styles.title}>Empresas</h2>

        <div className={styles.actions}>
          <button className="btn ghost" onClick={() => fetchAll()}>Atualizar</button>
          <button className="btn primary" onClick={() => setModalOpen(true)}>Nova Empresa</button>
        </div>
      </div>

      {loading && <p>Carregando...</p>}
      {error && <p>Erro ao carregar empresas: {error.message}</p>}

      <div className={styles.listGrid}>
        {/* ✅ VALIDAÇÃO ADICIONADA AQUI */}
        {items && items.length > 0 ? (
          items.map(e => (
            <EnterpriseCard key={e.id} empresa={e} />
          ))
        ) : (
          !loading && <p>Nenhuma empresa encontrada.</p>
        )}
      </div>

      {modalOpen && (
        <EntityModal
          onClose={() => setModalOpen(false)}
          handleSubmit={(e) => {
            e.preventDefault();
            const formData = new FormData(e.target);
            const data = Object.fromEntries(formData);
            handleSubmit(data);
          }}
          handleChange={() => {}}
          form={{}}
          fields={fields}
        />
      )}
    </div>
  );
}
