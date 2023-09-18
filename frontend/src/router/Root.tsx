import { Suspense } from 'react';
import { Outlet } from 'react-router-dom';
import { Header } from '~/components/@common/Header';
import LoadingIndicator from '~/components/@common/LoadingIndicator';
import Toast from '~/components/@common/Toast';
import useMediaQuery from '~/hooks/useMediaQuery';

function Root() {
  const { isMobile } = useMediaQuery();

  return (
    <>
      <Suspense fallback={<LoadingIndicator size={64} />}>
        {!isMobile && <Header />}
        <Outlet />
      </Suspense>
      <Toast />
    </>
  );
}

export default Root;
