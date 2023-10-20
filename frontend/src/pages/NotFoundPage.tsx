import { useNavigate } from 'react-router-dom';
import styled, { css } from 'styled-components';
import NotFound from '~/assets/not-found.svg';
import Footer from '~/components/@common/Footer';
import { Header, MobileHeader } from '~/components/@common/Header';
import BottomNavBar from '~/components/BottomNavBar';
import useMediaQuery from '~/hooks/useMediaQuery';
import { BORDER_RADIUS, FONT_SIZE } from '~/styles/common';

function NotFoundPage() {
  const { isMobile } = useMediaQuery();
  const navigator = useNavigate();

  const goHome = () => {
    navigator('/');
  };

  const errorSvgSize = isMobile ? '100%' : '550px';

  return (
    <StyledContainer isMobile={isMobile}>
      {isMobile ? <MobileHeader /> : <Header />}
      <StyledNotFoundPage isMobile={isMobile}>
        <section>
          <h1>이런!</h1>
          <div>
            <h3>죄송합니다.</h3>
            <h3>페이지를 찾을 수 없어요.</h3>
            <StyledHomeButton onClick={goHome} isMobile={isMobile}>
              홈으로 돌아가기
            </StyledHomeButton>
          </div>
        </section>
        <section>
          <NotFound width={errorSvgSize} height={errorSvgSize} />
        </section>
      </StyledNotFoundPage>
      {isMobile && (
        <div>
          <BottomNavBar isHide={false} />
        </div>
      )}
      {!isMobile && <Footer />}
    </StyledContainer>
  );
}

export default NotFoundPage;

const StyledContainer = styled.div<{ isMobile: boolean }>`
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  width: 100%;
  height: 100vh;
`;

const StyledNotFoundPage = styled.main<{ isMobile: boolean }>`
  display: flex;
  flex-direction: ${({ isMobile }) => (isMobile ? 'column' : 'row')};
  justify-content: center;
  align-items: center;
  gap: 3.6rem;

  position: relative;

  & > section:first-child {
    margin-top: 2rem;

    display: flex;
    flex-direction: column;
    align-items: ${({ isMobile }) => isMobile && 'center'};
    gap: 3rem;

    & > * {
      ${({ isMobile }) =>
        isMobile &&
        css`
          display: flex;
          flex-direction: column;
          align-items: center;
          gap: 2rem;
        `};
    }
  }
`;

const StyledHomeButton = styled.button<{ isMobile: boolean }>`
  margin-top: 2rem;

  padding: 1.6rem 4.8rem;

  border: none;
  border-radius: ${BORDER_RADIUS.sm};
  background: var(--primary-4);

  font-size: ${FONT_SIZE.md};

  &:hover {
    background: var(--primary-5);
  }
`;
