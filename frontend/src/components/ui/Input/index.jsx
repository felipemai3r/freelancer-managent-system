import React from "react";
import styles from "./Input.module.css";

function Input({ label, helper, ...rest }) {
  return (
    <div className={styles.wrapper}>
      {label && <label className={styles.label}>{label}</label>}
      <input className={styles.input} {...rest} />
      {helper && <p className={styles.helper}>{helper}</p>}
    </div>
  );
}

export default Input;
