import React from 'react';
import './LoginPage.css';
import {useForm} from 'react-hook-form';
import {useNavigate} from 'react-router-dom';
import logoImage from './logo.png';
import {handleLogin} from './HandleLogin';
import InputBox from '../../components/inputbox/InputBox';
import Button from '../../components/button/Button';

function LoginPage() {
    const {register, handleSubmit} = useForm();
    const navigate = useNavigate();

    const onSubmit = async (data) => {
        await handleLogin(data, navigate);
    };

    return (
        <div className="container">
            <div><img src={logoImage} alt="Logo" width={600} height={230}/></div>
            <form onSubmit={handleSubmit(onSubmit)}>
                <InputBox
                    type="text"
                    register={register('username', { required: true })}
                    placeholder="아이디"
                />
                <InputBox
                    type="password"
                    register={register('password', { required: true })}
                    placeholder="비밀번호"
                />
                <Button type="submit">로그인</Button>
            </form>
        </div>
    );
}

export default LoginPage;
