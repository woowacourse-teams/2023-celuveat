import { createGlobalStyle } from 'styled-components';
import resetCSS from './reset';
import baseCSS from './base';
import fonts from './fonts';

const GlobalStyles = createGlobalStyle`
  ${resetCSS}
  ${fonts}
  ${baseCSS}
`;

export default GlobalStyles;
