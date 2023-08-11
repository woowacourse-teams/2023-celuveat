import { Link } from 'react-router-dom';
import styled, { css } from 'styled-components';
import React from 'react';
import { OAUTH_BUTTON_MESSAGE, OAUTH_LINK } from '~/constants/api';

import KaKao from '~/assets/icons/oauth/kakao.svg';
import Naver from '~/assets/icons/oauth/naver.svg';
import Google from '~/assets/icons/oauth/google.svg';
import { Oauth } from '~/@types/oauth.types';

interface LoginButtonProps {
  type: Oauth;
}

const LoginIcon: Record<string, React.ReactNode> = {
  naver: <Naver width={14} />,
  kakao: <KaKao />,
  google: <Google />,
};

function LoginButton({ type }: LoginButtonProps) {
  return (
    <StyledLoginButtonWrapper type={type} to={OAUTH_LINK[type]} target="_blank">
      <div>{LoginIcon[type]}</div>
      <StyledLoginButtonText>{OAUTH_BUTTON_MESSAGE[type]}</StyledLoginButtonText>
    </StyledLoginButtonWrapper>
  );
}

export default LoginButton;

const StyledLoginButtonWrapper = styled(Link)<LoginButtonProps>`
  display: flex;

  width: 100%;
  height: fit-content;

  padding: 2.3rem 1.3rem;

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
`;
