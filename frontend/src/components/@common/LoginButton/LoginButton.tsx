import { useNavigate } from 'react-router-dom';
import styled, { css } from 'styled-components';
import React, { useCallback, useEffect, useState } from 'react';
import { OAUTH_BUTTON_MESSAGE, OAUTH_LINK } from '~/constants/api';

import KaKao from '~/assets/icons/oauth/kakao.svg';
import Naver from '~/assets/icons/oauth/naver.svg';
import Google from '~/assets/icons/oauth/google.svg';
import { Oauth } from '~/@types/oauth.types';
import useTokenState from '~/hooks/store/useTokenState';
import { FONT_SIZE } from '~/styles/common';
import { getAccessToken } from '~/api/oauth';

interface OauthCodeResponse {
  jsessionId: string;
}

interface LoginButtonProps {
  type: Oauth;
}

const LoginIcon: Record<string, React.ReactNode> = {
  naver: <Naver width={24} />,
  kakao: <KaKao width={24} />,
  google: <Google width={24} />,
};

function LoginButton({ type }: LoginButtonProps) {
  const [popUp, setPopup] = useState<Window | null>(null);

  const navigator = useNavigate();
  const [updateOauth, updateToken] = useTokenState(state => [state.updateOauth, state.updateToken]);

  const handleOpenGithubOAuthPopup = useCallback((): void => {
    const title = '로그인 중...';
    const width = 500;
    const height = 400;
    const left = window.screenX + (window.outerWidth - width) / 2;
    const top = window.screenY + (window.outerHeight - height) / 2.5;
    const url = OAUTH_LINK[type];
    const popup = window.open(url, title, `width=${width},height=${height},left=${left},top=${top}`);

    setPopup(popup);
    updateOauth(type);
  }, [popUp]);

  const clearPopup = useCallback((): void => {
    if (popUp !== null) {
      popUp.close();
    }

    setPopup(null);
  }, [popUp]);

  useEffect(() => {
    if (popUp === null) {
      return;
    }

    const oauthCodeListener = (e: MessageEvent<any>): void => {
      if (e.origin !== window.location.origin) return;

      const { code } = e.data;

      getAccessToken(type, code)
        .then(response => {
          const { jsessionId } = response.data as OauthCodeResponse;
          updateToken(jsessionId);
        })
        .catch(() => {
          navigator('/fail');
        })
        .finally(() => clearPopup());
    };

    window.addEventListener('message', oauthCodeListener, false);

    // eslint-disable-next-line consistent-return
    return () => {
      window.removeEventListener('message', oauthCodeListener);
      clearPopup();
    };
  }, [popUp]);

  return (
    <StyledLoginButtonWrapper type={type} onClick={handleOpenGithubOAuthPopup}>
      <div>{LoginIcon[type]}</div>
      <StyledLoginButtonText>{OAUTH_BUTTON_MESSAGE[type]}</StyledLoginButtonText>
    </StyledLoginButtonWrapper>
  );
}

export default LoginButton;

const StyledLoginButtonWrapper = styled.div<LoginButtonProps>`
  display: flex;
  align-items: center;

  width: 100%;
  height: fit-content;

  padding: 2.4rem 1.6rem;

  border-radius: 12px;

  font-size: 1.4rem;
  font-weight: 600;
  text-decoration: none;

  ${({ type }) =>
    type === 'naver' &&
    css`
      background: #03c759;

      color: #fff;
    `}

  ${({ type }) =>
    type === 'kakao' &&
    css`
      background: #fee500;
    `}

  ${({ type }) =>
    type === 'google' &&
    css`
      border: 1px solid var(--gray-3);
    `}

  cursor: pointer;
  transition: box-shadow 0.2s cubic-bezier(0.2, 0, 0, 1), transform 0.1s cubic-bezier(0.2, 0, 0, 1);
`;

const StyledLoginButtonText = styled.span`
  margin: 0 auto;

  color: inherit;
  font-size: ${FONT_SIZE.md};
`;
