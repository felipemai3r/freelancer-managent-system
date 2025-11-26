import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import EmpresaService from "../../api/empresaService";
import styles from "./CompanyDetail.module.css";
import "animate.css";

export default function CompanyDetail() {
  const { id } = useParams();
  const [empresa, setEmpresa] = useState(null);

  useEffect(() => {
    // Não existe GET /empresa/{id}, então filtramos
    EmpresaService.list().then(res => {
      const found = res.data.find(e => String(e.id) === String(id));
      setEmpresa(found);
    });
  }, [id]);

  if (!empresa) return <p className={styles.loading}>Carregando...</p>;

  return (
    <div className={`${styles.page} animate__animated animate__fadeInUp`}>
      <h2 className={styles.title}>Empresa #{empresa.id}</h2>

      <div className={styles.card}>
        <p><strong>Nome Fantasia:</strong> {empresa.nomeEmpresa}</p>
        <p><strong>CNPJ:</strong> {empresa.cnpj}</p>
        <p><strong>Email:</strong> {empresa.pessoa.email}</p>
        <p><strong>Setor:</strong> {empresa.setor || "Não informado"}</p>
      </div>

      {/* Habilitar no futuro */}
      {/* <button className="btn">Editar</button> */}
      {/* <button className="btn danger">Excluir</button> */}
    </div>
  );
}
