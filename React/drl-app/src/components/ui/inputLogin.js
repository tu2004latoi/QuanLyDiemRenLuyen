import React from 'react';

const InputLogin = ({
  label,
  type = 'text',
  value,
  onChange,
  placeholder = '',
  className = '',
  disabled = false,
}) => {
  return (
    <div className="mb-4">
      {label && (
        <label className="block text-sm font-medium text-gray-700 mb-1">
          {label}
        </label>
      )}
      <input
        type={type}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        disabled={disabled}
        className={`
          w-full px-4 py-2
          border border-gray-300 rounded-xl
          focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent
          disabled:bg-gray-100 disabled:cursor-not-allowed
          transition duration-300
          ${className}
        `}
      />
    </div>
  );
};

export default InputLogin;
