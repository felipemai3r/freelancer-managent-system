import React, { useEffect, useState } from "react";
import EmpresaService from "../../api/empresaService";
import useCrud from "../../hooks/useCrud";
import EnterpriseCard from "../../components/EnterpriseCard";
import EntityModal from "../../components/EntityModal";

import styles from "./Companies.module.css";
import "animate.css";

const fields = [
  { name: "nomeFantasia", label: "Nome Fantasia", required: true, placeholder: "Nome fantasia" },
  { name: "razaoSocial", label: "Razão Social", required: true, placeholder: "Razão social" },
  { name: "cnpj", label: "CNPJ", required: true, placeholder: "00.000.000/0000-00" },
  { name: "email", label: "Email", required: false, placeholder: "contato@empresa.com" },
  { name: "setor", label: "Setor", required: false, placeholder: "Setor da empresa" },
];

export default function Companies() {
  const { items, loading, error, fetchAll, create } = useCrud(EmpresaService);
  const [modalOpen, setModalOpen] = useState(false);

  useEffect(() => { fetchAll(); }, [fetchAll]);

  const handleSubmit = async (data) => {
    try {
      await create(data);
      setModalOpen(false);
    } catch (err) {
      console.error("Erro ao salvar empresa", err);
      alert("Erro ao salvar: " + (err.message || err));
    }
  };

  return (
    <div className={`${styles.page} animate__animated animate__fadeInUp`}>
      <div className={styles.header}>
        <h2 className={styles.title}>Empresas</h2>

        <div className={styles.actions}>
          <button className="btn ghost" onClick={() => fetchAll()}>Atualizar</button>
          <button className="btn primary" onClick={() => setModalOpen(true)}>Nova Empresa</button>
        </div>
      </div>

      {loading && <p className={styles.loading}>Carregando...</p>}
      {error && <p className={styles.error}>Erro ao carregar empresas.</p>}

      <div className={styles.grid}>
        {items.map(e => (
          <div key={e.id} className={styles.itemWrapper}>
            <EnterpriseCard empresa={e} />

            {/* O BACKEND NÃO IMPLEMENTOU PUT / DELETE */}
            {/*
            <div className={styles.row}>
              <button className="btn" onClick={() => handleEdit(e)}>Editar</button>
              <button className="btn danger" onClick={() => handleDelete(e.id)}>Remover</button>
              <a className="btn" href={`/empresas/${e.id}`}>Ver</a>
            </div>
            */}
          </div>
        ))}
      </div>

      <EntityModal
        open={modalOpen}
        onClose={() => setModalOpen(false)}
        onSubmit={handleSubmit}
        initial={{}}
        fields={fields}
      />
    </div>
  );
}
