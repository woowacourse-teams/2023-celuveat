import React, { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { BASE_URL } from '~/constants/api';

interface OauthRedirectProps {
  type: 'google' | 'kakao' | 'naver';
}

function OauthRedirectPage({ type }: OauthRedirectProps) {
  const location = useLocation();
  const navigate = useNavigate();

  const handleOAuth = async (code: string) => {
    try {
      // 카카오로부터 받아온 code를 서버에 전달하여 카카오로 회원가입 & 로그인한다
      const response = await fetch(`${BASE_URL}/oauth/login/${type}?code=${code}`);
      await response.json(); // 응답 데이터

      alert('로그인 성공');

      navigate('/success');
    } catch (error) {
      navigate('/fail'); // 실패 페이지
    }
  };

  useEffect(() => {
    const searchParams = new URLSearchParams(location.search);
    const code = searchParams.get('code'); // 카카오는 Redirect 시키면서 code를 쿼리 스트링으로 준다.

    if (code) {
      alert(`CODE = ${code}`);
      handleOAuth(code);
    }
  }, [location]);

  return (
    <div>
      <div>Processing...</div>
    </div>
  );
}

export default OauthRedirectPage;
