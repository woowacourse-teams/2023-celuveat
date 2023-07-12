import React from 'react';
import '../../common/Common.css';
import './Button.css';

function Button({ type, children }) {
    return (
        <button className="card" type={type}>
            {children}
        </button>
    );
}

export default Button;
