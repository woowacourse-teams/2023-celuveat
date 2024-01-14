import { Suspense } from 'react';
import { styled } from 'styled-components';
import LoadingIndicator from '~/components/@common/LoadingIndicator';

import Router from './router/Router';

function App() {
  return (
    <Suspense
      fallback={
        <StyledProcessing>
          <LoadingIndicator size={64} />
        </StyledProcessing>
      }
    >
      <Router />
    </Suspense>
  );
}

export default App;

const StyledProcessing = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  height: 100vh;
`;
