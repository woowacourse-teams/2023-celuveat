import { Outlet, ScrollRestoration } from 'react-router-dom';
import { Modal } from 'celuveat-ui-library';
import Footer from '~/components/@common/Footer';
import { Header, MobileHeader } from '~/components/@common/Header';

import useMediaQuery from '~/hooks/useMediaQuery';
import BottomNavBar from '~/components/BottomNavBar';
import useScrollDirection from '~/hooks/useScrollDirection';
import useBooleanState from '~/hooks/useBooleanState';
import Toast from '~/components/@common/Toast';

function Root() {
  const scrollDirection = useScrollDirection();
  const { value: isListShowed } = useBooleanState(false);

  const { isMobile } = useMediaQuery();

  return (
    <>
      <>
        {isMobile ? <MobileHeader /> : <Header />}

        <Outlet />

        {isMobile && <BottomNavBar isHide={isListShowed && scrollDirection.y === 'down'} />}
        {!isMobile && <Footer />}
      </>
      <ScrollRestoration />
      <Toast />
      <Modal portalElementId="portal" isBottom={isMobile} blockScrollOnMount />
    </>
  );
}

export default Root;
