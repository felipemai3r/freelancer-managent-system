import React from "react";
import styles from "./Spinner.module.css";

function Spinner() {
  return (
    <div className={styles.wrapper}>
      <div className={styles.dot} />
      <div className={styles.dot} />
      <div className={styles.dot} />
    </div>
  );
}

export default Spinner;
