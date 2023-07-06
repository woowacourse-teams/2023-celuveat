import { StrictMode } from 'react';
import ReactDOM from 'react-dom/client';
import App from '~/App';
import GlobalStyles from './styles/GlobalStyles';

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);

root.render(
  <StrictMode>
    <GlobalStyles />
    <App />
  </StrictMode>,
);
