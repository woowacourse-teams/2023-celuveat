import styled, { css } from 'styled-components';

import useMediaQuery from '~/hooks/useMediaQuery';

import { FONT_SIZE } from '~/styles/common';

interface LoginPageUIProps {
  title: string;
  children: React.ReactNode;
}

function LoginPageUI({ title, children }: LoginPageUIProps) {
  const { isMobile } = useMediaQuery();

  return (
    <StyledMobileLayout>
      <StyledContent isMobile={isMobile}>
        <StyledTitle>{title}</StyledTitle>
        {children}
      </StyledContent>
    </StyledMobileLayout>
  );
}

export default LoginPageUI;

const StyledMobileLayout = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;

  width: 100%;
  min-height: 100dvh;
`;

const StyledTitle = styled.h2`
  padding-bottom: 2rem;

  font-size: ${FONT_SIZE.md};
  border-bottom: 1px solid var(--gray-3);

  text-align: center;

  margin-bottom: 2rem;
`;

const StyledContent = styled.div<{ isMobile: boolean }>`
  display: flex;
  flex-direction: column;

  position: relative;
  z-index: 10;

  min-height: 100px;

  padding: 2rem;

  border: 1px solid var(--gray-3);
  border-radius: 12px;
  background: var(--white);

  ${({ isMobile }) =>
    isMobile
      ? css`
          width: 100%;
        `
      : css`
          width: 33%;
          min-width: 500px;
        `}
`;
