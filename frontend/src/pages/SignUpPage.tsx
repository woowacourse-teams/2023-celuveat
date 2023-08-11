import styled from 'styled-components';

import Footer from '~/components/@common/Footer';
import Header from '~/components/@common/Header';
import LoginModalContent from '~/components/LoginModalContent';
import { FONT_SIZE } from '~/styles/common';

function SignUpPage() {
  return (
    <StyledMobileLayout>
      <Header />
      <StyledContent>
        <StyledTitle>로그인 및 회원가입</StyledTitle>
        <LoginModalContent />
      </StyledContent>
      <Footer />
    </StyledMobileLayout>
  );
}

export default SignUpPage;

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

const StyledContent = styled.div`
  display: flex;
  flex-direction: column;

  position: relative;
  z-index: 10;

  width: 33%;
  min-width: 500px;
  max-width: 600px;
  min-height: 100px;

  padding: 2rem;

  border: 1px solid var(--gray-3);
  border-radius: 12px;
  background: var(--white);
`;
