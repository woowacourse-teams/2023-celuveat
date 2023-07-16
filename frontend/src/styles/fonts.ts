import { css } from 'styled-components';
import SUITRegular from '~/assets/fonts/SUIT-Regular.woff2';
import SUITMedium from '~/assets/fonts/SUIT-Medium.woff2';
import SUITBold from '~/assets/fonts/SUIT-Bold.woff2';

const fonts = css`
  @font-face {
    font-style: normal;
    font-weight: normal;
    font-family: SUIT-Regular;
    src: url(${SUITRegular}) format('woff2');
  }

  @font-face {
    font-style: normal;
    font-weight: normal;
    font-family: SUIT-Medium;
    src: url(${SUITMedium}) format('woff2');
  }

  @font-face {
    font-style: normal;
    font-weight: normal;
    font-family: SUIT-Bold;
    src: url(${SUITBold}) format('woff2');
  }
`;

export default fonts;
