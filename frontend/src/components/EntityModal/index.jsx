import React from 'react';
import Button from '../ui/Button';
import Input from '../ui/Input';
import styles from './EntityModal.module.css';

const EntityModal = ({ onClose, handleSubmit, handleChange, form, fields }) => {
  const onFormSubmit = (e) => {
    e.preventDefault();
    handleSubmit(form);
  };

  return (
    <div className={styles.backdrop} onClick={onClose}>
      <div 
        className={`${styles.modal} animate__animated animate__zoomIn animate__faster`}
        onClick={(e) => e.stopPropagation()}
      >
        <button className={styles.closeBtn} onClick={onClose} aria-label="Fechar">
          ×
        </button>

        <form onSubmit={onFormSubmit}>
          <h3 className={styles.title}>Preencha os dados</h3>

          <div className={styles.fields}>
            {fields.map((field) => (
              <div key={field.name} className={styles.field}>
                <label htmlFor={field.name}>
                  {field.label}
                  {field.required && <span className={styles.required}> *</span>}
                </label>
                <Input
                  id={field.name}
                  name={field.name}
                  value={form[field.name] || ''}
                  onChange={handleChange}
                  placeholder={field.placeholder || field.label}
                  required={field.required}
                />
              </div>
            ))}
          </div>

          <div className={styles.actions}>
            <Button type="button" variant="ghost" onClick={onClose}>
              Cancelar
            </Button>
            <Button type="submit" variant="primary">
              Salvar
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default EntityModal;
