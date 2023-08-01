import { useMutation } from '@tanstack/react-query';
import React, { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

import getAccessToken from '~/api/oauth';
import useTokenState from '~/hooks/store/useTokenState';

interface OauthRedirectProps {
  type: 'google' | 'kakao' | 'naver';
}

interface OauthCodeResponse {
  jsessionId: string;
}

function OauthRedirectPage({ type }: OauthRedirectProps) {
  const location = useLocation();
  const navigator = useNavigate();

  const updateToken = useTokenState(state => state.updateToken);

  const searchParams = new URLSearchParams(location.search);
  const code = searchParams.get('code');

  const oauthTokenMutation = useMutation({
    mutationFn: () => getAccessToken(type, code),
    onSuccess: (data: OauthCodeResponse) => {
      const { jsessionId } = data;

      updateToken(jsessionId);

      navigator('/');
    },
    onError: () => {
      navigator('/fail');
    },
  });

  useEffect(() => {
    if (code) {
      oauthTokenMutation.mutate();
    }
  }, [code]);

  return (
    <div>
      <div>Processing...</div>
    </div>
  );
}

export default OauthRedirectPage;
