import React from 'react';

const Button = ({ 
  children, 
  variant = 'primary', 
  type = 'button', 
  disabled = false,
  onClick,
  className = '',
  ...props 
}) => {
  return (
    <button
      type={type}
      disabled={disabled}
      onClick={onClick}
      className={`btn ${variant} ${className}`}
      {...props}
    >
      {children}
    </button>
  );
};

export default Button;
