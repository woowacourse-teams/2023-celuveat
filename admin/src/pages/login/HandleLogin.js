import { JSESSIONID, SERVER_DOMAIN } from '../../common/Config.js';

export const handleLogin = async (data, navigate) => {
    try {
        const response = await fetch(SERVER_DOMAIN + '/admin/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });

        if (response.ok) {
            const jsonResponse = await response.json();
            const sessionId = jsonResponse.sessionId;
            localStorage.setItem(JSESSIONID, sessionId);
            navigate('/admin');
        } else {
            alert('로그인에 실패했습니다. 아이디와 비밀번호를 확인하세요.');
        }
    } catch (error) {
        alert('로그인에 실패했습니다. 뭔가 문제가 있습니다.');
    }
};
