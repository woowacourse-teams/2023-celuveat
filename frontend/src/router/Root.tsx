import { Suspense } from 'react';
import styled from 'styled-components';
import { Outlet } from 'react-router-dom';
import Footer from '~/components/@common/Footer';
import { Header, MobileHeader } from '~/components/@common/Header';
import LoadingIndicator from '~/components/@common/LoadingIndicator';
import Toast from '~/components/@common/Toast';
import useMediaQuery from '~/hooks/useMediaQuery';

function Root() {
  const { isMobile } = useMediaQuery();

  return (
    <>
      <Suspense
        fallback={
          <StyledProcessing>
            <LoadingIndicator size={64} />
          </StyledProcessing>
        }
      >
        {isMobile ? <MobileHeader /> : <Header />}
        <Outlet />
        {!isMobile && <Footer />}
      </Suspense>
      <Toast />
    </>
  );
}

const StyledProcessing = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  height: 100vh;
`;

export default Root;
