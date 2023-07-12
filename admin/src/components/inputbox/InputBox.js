import React from 'react';
import '../../common/Common.css';
import './InputBox.css';

function InputBox({ type, register, placeholder }) {
    return (
        <div>
            <input
                className="card"
                type={type}
                {...register}
                placeholder={placeholder}
            />
        </div>
    );
}

export default InputBox;
