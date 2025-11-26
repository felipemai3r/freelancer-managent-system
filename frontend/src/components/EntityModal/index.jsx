import React from "react";
import styles from "./EntityModal.module.css";
import "animate.css";

function EntityModal({ onClose, handleSubmit, handleChange, form, fields }) {
  return (
    <div className={styles.backdrop}>
      <div className={`${styles.modal} animate__animated animate__zoomIn`}>
        <button className={styles.closeBtn} onClick={onClose}>×</button>

        <form onSubmit={handleSubmit}>
          {fields.map((f) => (
            <label className={styles.formLabel} key={f.name}>
              {f.label}
              <input
                className={styles.formInput}
                name={f.name}
                value={form[f.name] ?? ""}
                onChange={handleChange}
                placeholder={f.placeholder || ""}
                required={!!f.required}
              />
            </label>
          ))}

          <div style={{ display: "flex", gap: ".5rem", marginTop: ".75rem" }}>
            <button type="button" className="btn ghost" onClick={onClose}>
              Cancelar
            </button>
            <button type="submit" className="btn primary">
              Salvar
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default React.memo(EntityModal);
