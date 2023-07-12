import React, {useEffect} from 'react';
import {JSESSIONID} from '../../common/Config.js';
import {useNavigate} from 'react-router-dom';

function MainPage() {
    const navigate = useNavigate();

    useEffect(() => {
        const sessionId = localStorage.getItem(JSESSIONID);
        if (!sessionId) {
            navigate('/admin/login');
        }
    }, [navigate]);

    return (
        <div>
            관리자 페이지 내용
        </div>
    );
}

export default MainPage;
