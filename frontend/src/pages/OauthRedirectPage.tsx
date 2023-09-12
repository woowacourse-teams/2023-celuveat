import { useMutation } from '@tanstack/react-query';
import React, { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import useUser from '~/hooks/server/useUser';

interface OauthRedirectProps {
  type: 'google' | 'kakao' | 'naver';
}

function OauthRedirectPage({ type }: OauthRedirectProps) {
  const location = useLocation();
  const navigator = useNavigate();
  const { getAccessToken } = useUser();

  const searchParams = new URLSearchParams(location.search);
  const code = searchParams.get('code');

  const oauthTokenMutation = useMutation({
    mutationFn: () => getAccessToken(type, code),
    onSuccess: () => {
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
