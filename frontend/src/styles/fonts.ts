import { css } from 'styled-components';
import SUITRegular from '~/assets/fonts/SUIT-Regular.woff2';
import SUITMedium from '~/assets/fonts/SUIT-Medium.woff2';
import SUITBold from '~/assets/fonts/SUIT-Bold.woff2';

const fonts = css`
  @font-face {
    font-family: SUIT-Regular;
    font-weight: normal;
    font-style: normal;
    src: url(${SUITRegular}) format('woff2');
  }

  @font-face {
    font-family: SUIT-Medium;
    font-weight: normal;
    font-style: normal;
    src: url(${SUITMedium}) format('woff2');
  }

  @font-face {
    font-family: SUIT-Bold;
    font-weight: normal;
    font-style: normal;
    src: url(${SUITBold}) format('woff2');
  }
`;

export default fonts;
