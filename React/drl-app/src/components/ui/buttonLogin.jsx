import React from 'react';

const ButtonLogin = ({
  children,
  onClick,
  type = 'button',
  className = '',
  disabled = false,
}) => {
  return (
    <button
      type={type}
      onClick={onClick}
      disabled={disabled}
      className={`
        inline-flex items-center justify-center
        px-5 py-2.5
        bg-blue-600 text-white font-medium
        rounded-xl shadow-md
        hover:bg-blue-700 hover:shadow-lg
        disabled:bg-gray-400 disabled:cursor-not-allowed
        transition duration-300 ease-in-out
        focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2
        ${className}
      `}
    >
      {children}
    </button>
  );
};

export default ButtonLogin;
