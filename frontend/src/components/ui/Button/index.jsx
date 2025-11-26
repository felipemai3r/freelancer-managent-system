import React from "react";
import styles from "./Button.module.css";

function Button({ children, variant = "primary", ...rest }) {
  return (
    <button
      {...rest}
      className={`${styles.btn} ${styles[variant]}`}
    >
      {children}
    </button>
  );
}

export default Button;
