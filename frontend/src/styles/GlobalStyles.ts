import { createGlobalStyle } from 'styled-components';
import reset from './reset';
import base from './base';
import fonts from './fonts';

const GlobalStyles = createGlobalStyle`
  ${reset}
  ${fonts}
  ${base}
`;

export default GlobalStyles;
