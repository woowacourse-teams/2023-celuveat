import { styled, css } from 'styled-components';
import { useLocation } from 'react-router-dom';
import { isEqual } from '~/utils/compare';
import { OAUTH_BUTTON_MESSAGE, OAUTH_LINK } from '~/constants/api';

import KaKao from '~/assets/icons/oauth/kakao.svg';
import Naver from '~/assets/icons/oauth/naver.svg';
import Google from '~/assets/icons/oauth/google.svg';

import { FONT_SIZE } from '~/styles/common';
import usePathNameState from '~/hooks/store/usePathnameState';
import { getUrlStringWithQuery } from '~/utils/getQueryString';
import { MSW_GET_OAUTH_CODE_URL, MSW_LOGIN_URL } from '~/constants/url';

import type { Oauth } from '~/@types/oauth.types';

interface LoginButtonProps {
  type: Oauth;
}

const LoginIcon: Record<string, React.ReactNode> = {
  naver: <Naver width={24} />,
  kakao: <KaKao width={24} />,
  google: <Google width={24} />,
};

const doLogin = (type: Oauth) => {
  if (isEqual(process.env.NODE_ENV, 'development')) {
    window.location.href = MSW_LOGIN_URL;
    window.location.href = MSW_GET_OAUTH_CODE_URL;
    return;
  }

  window.location.href = OAUTH_LINK[type];
};

function LoginButton({ type }: LoginButtonProps) {
  const location = useLocation();
  const setPath = usePathNameState(state => state.setPath);

  const loginBeforeUrl = isEqual(location.pathname, '/signUp')
    ? getUrlStringWithQuery(location.state.from)
    : getUrlStringWithQuery(location.pathname);

  const onClick = () => {
    setPath(loginBeforeUrl);
    doLogin(type);
  };

  return (
    <StyledLoginButtonWrapper type={type} onClick={onClick}>
      <div>{LoginIcon[type]}</div>
      <StyledLoginButtonText>{OAUTH_BUTTON_MESSAGE[type]}</StyledLoginButtonText>
    </StyledLoginButtonWrapper>
  );
}

export default LoginButton;

const StyledLoginButtonWrapper = styled.button<LoginButtonProps>`
  display: flex;
  align-items: center;

  width: 100%;
  height: fit-content;

  padding: 2.4rem 1.6rem;

  border: none;
  border-radius: 12px;
  background: var(--white);

  font-size: 1.4rem;
  font-weight: 600;
  text-decoration: none;

  & + & {
    margin-top: 1.2rem;
  }

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
