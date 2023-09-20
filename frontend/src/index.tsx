import ReactDOM from 'react-dom/client';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import App from '~/App';
import GlobalStyles from './styles/GlobalStyles';
import { worker } from './mocks/browser';

import '~/assets/fonts/font.css';

const DEV = process.env.NODE_ENV === 'development';

if (DEV) {
  worker.start();
}

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);

const queryClient = new QueryClient();

root.render(
  <>
    <GlobalStyles />
    <QueryClientProvider client={queryClient}>
      <App />
      {DEV && <ReactQueryDevtools />}
    </QueryClientProvider>
  </>,
);
