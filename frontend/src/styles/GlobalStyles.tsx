import { createGlobalStyle } from 'styled-components';
import resetCSS from './reset';
import baseCSS from './base';

const GlobalStyles = createGlobalStyle`
  ${resetCSS}
  ${baseCSS}
`;

export default GlobalStyles;
