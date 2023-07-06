import { createGlobalStyle } from 'styled-components';
import resetCSS from './reset';
import baseCSS from './base';

const GlobalStyle = createGlobalStyle`
  ${resetCSS}
  ${baseCSS}
`;

export default GlobalStyle;
