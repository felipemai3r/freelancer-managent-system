import React from 'react';
import styles from './Spinner.module.css';

const Spinner = ({ size = 'medium' }) => {
  return (
    <div className={`${styles.spinner} ${styles[size]}`}>
      <div className={styles.bounce1}></div>
      <div className={styles.bounce2}></div>
      <div className={styles.bounce3}></div>
    </div>
  );
};

export default Spinner;
