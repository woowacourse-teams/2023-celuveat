import React, { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { styled } from 'styled-components';
import LoadingIndicator from '~/components/@common/LoadingIndicator';
import useAuth from '~/hooks/server/useAuth';

interface OauthRedirectProps {
  type: 'google' | 'kakao' | 'naver';
}

function OauthRedirectPage({ type }: OauthRedirectProps) {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const code = searchParams.get('code');

  const { doLoginMutation } = useAuth();

  useEffect(() => {
    if (code) {
      doLoginMutation({ type, code });
    }
  }, [code]);

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
