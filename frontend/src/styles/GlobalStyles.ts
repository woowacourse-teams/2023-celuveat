import { createGlobalStyle } from 'styled-components';
import reset from './reset';
import base from './base';

const GlobalStyles = createGlobalStyle`
  ${reset}
  ${base}
`;

export default GlobalStyles;
