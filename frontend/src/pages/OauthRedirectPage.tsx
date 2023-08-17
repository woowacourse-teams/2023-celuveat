import { useQuery } from '@tanstack/react-query';
import React, { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

import { getAccessToken } from '~/api/oauth';
import useTokenState from '~/hooks/store/useTokenState';
import MainPage from './MainPage';

interface OauthRedirectProps {
  type: 'google' | 'kakao' | 'naver';
}

function OauthRedirectPage({ type }: OauthRedirectProps) {
  const location = useLocation();
  const navigator = useNavigate();

  const [updateOauth, updateToken] = useTokenState(state => [state.updateOauth, state.updateToken]);

  const searchParams = new URLSearchParams(location.search);
  const code = searchParams.get('code');

  const {
    data: { jsessionId },
    isLoading,
  } = useQuery({
    queryKey: ['oauthToken', type, code],
    queryFn: () => getAccessToken(type, code),
    onError: () => {
      navigator('/fail');
    },
  });

  useEffect(() => {
    updateToken(jsessionId);
    updateOauth(type);
    navigator('/');
  }, [jsessionId]);

  if (isLoading) {
    return (
      <div>
        <div>Processing...</div>
      </div>
    );
  }

  return <MainPage />;
}

export default OauthRedirectPage;
