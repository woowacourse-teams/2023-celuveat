import { useMutation } from '@tanstack/react-query';
import React, { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { getAccessToken } from '~/api/user';
import LoadingIndicator from '~/components/@common/LoadingIndicator';

interface OauthRedirectProps {
  type: 'google' | 'kakao' | 'naver';
}

function OauthRedirectPage({ type }: OauthRedirectProps) {
  const location = useLocation();
  const navigator = useNavigate();

  const searchParams = new URLSearchParams(location.search);
  const code = searchParams.get('code');

  const oauthTokenMutation = useMutation({
    mutationFn: () => getAccessToken(type, code),
    onSuccess: () => {
      navigator('/');
    },
    onError: () => {
      navigator('/');
      alert('서버 문제로 인해 로그인에 실패하였습니다.');
    },
  });

  useEffect(() => {
    if (code) {
      oauthTokenMutation.mutate();
    }
  }, [code]);

  useEffect(() => {
    if (process.env.NODE_ENV === 'development') {
      fetch('https://d.api.celuveat.com/login/local?id=abc').then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }

        navigator('/');
      });
    }
  }, []);

  return (
    <StyledProcessing>
      <LoadingIndicator size={32} />
    </StyledProcessing>
  );
}

export default OauthRedirectPage;

const StyledProcessing = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  height: 100vh;
`;
