/* stylelint-disable declaration-property-unit-allowed-list */
import { Suspense, lazy } from 'react';
import { styled } from 'styled-components';
import { Outlet, ScrollRestoration } from 'react-router-dom';
import { HelmetProvider } from 'react-helmet-async';
import Footer from '~/components/@common/Footer';
import { Header, MobileHeader } from '~/components/@common/Header';
import LoadingIndicator from '~/components/@common/LoadingIndicator';
import useMediaQuery from '~/hooks/useMediaQuery';
import BottomNavBar from '~/components/BottomNavBar';
import useScrollDirection from '~/hooks/useScrollDirection';
import useBooleanState from '~/hooks/useBooleanState';

const Toast = lazy(() => import('~/components/@common/Toast'));

function Root() {
  const scrollDirection = useScrollDirection();
  const { value: isListShowed } = useBooleanState(false);

  const { isMobile } = useMediaQuery();
  const helmetContext = {};

  return (
    <HelmetProvider context={helmetContext}>
      <Suspense
        fallback={
          <StyledProcessing>
            <LoadingIndicator size={64} />
          </StyledProcessing>
        }
      >
        {isMobile ? <MobileHeader /> : <Header />}

        <Outlet />

        {isMobile && <BottomNavBar isHide={isListShowed && scrollDirection.y === 'down'} />}
        {!isMobile && <Footer />}
      </Suspense>
      <ScrollRestoration />
      <Toast />
    </HelmetProvider>
  );
}

const StyledProcessing = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  height: 100vh;
`;

export default Root;
