import { css } from 'styled-components';

const baseCSS = css`
  :root {
    --primary-1: #fff2ee;
    --primary-2: #ffe5dd;
    --primary-3: #ffcabb;
    --primary-4: #ffb098;
    --primary-5: #ff9576;
    --primary-6: #ff7b54;
    --primary-7: #cc6243;
    --primary-8: #994a32;
    --primary-9: #663122;
    --primary-10: #331911;
    --primary-3-transparent-25: rgba(255, 178, 107, 0.25);

    --red-1: #fdaaaa;
    --red-2: #f66161;
    --red-3: #de360b;
    --red-4: #bf0711;
    --red-5: #9d091e;

    --gray-1: #eee;
    --gray-2: #e0e0e0;
    --gray-3: #bdbdbd;
    --gray-4: #393939;
    --gray-5: #2c2c2c;
    --gray-6: #161616;

    --orange: #ffb26b;
    --yellow: #ffd56f;
    --black: #000;
    --white: #fff;

    --shadow: 0 1px 16px 0 rgba(66, 66, 66, 0.1);

    --padding-small: 4px;
    --padding-medium: 8px;
    --padding-large: 16px;

    --font-x-small: 0.8rem;
    --font-small: 1.2rem;
    --font-medium: 1.6rem;
    --font-large: 2rem;
  }

  * {
    color: #424242;
    font-family: Roboto, system-ui, 'Segoe UI', Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji',
      'Segoe UI Symbol';
    font-weight: 400;
    font-size: 62.5%;
  }

  body {
    font-size: 1.6rem;
  }

  h1 {
    font-size: 40px;
    font-weight: bold;
  }

  h2 {
    font-size: 32px;
    font-weight: bold;
  }

  h3 {
    font-size: 28px;
    font-weight: bold;
  }

  h4 {
    font-size: 24px;
    font-weight: bold;
  }

  h5 {
    font-size: 20px;
    font-weight: bold;
  }
`;

export default baseCSS;
