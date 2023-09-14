import { css } from 'styled-components';

const base = css`
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
    --primary-3-transparent-25: rgb(255 178 107 / 25%);
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
    --orange-1: #ffb26b;
    --orange-2: #eb982d;
    --yellow: #ffd56f;
    --black: #424242;
    --white: #fff;
    --shadow: 0 1px 16px 0 rgb(66 66 66 / 10%);
    --map-shadow: 0 6px 16px rgb(0 0 0 / 12%);
  }

  * {
    color: var(--black);
    font-weight: 400;
    box-sizing: border-box;
  }

  html {
    font-family: SUIT-Regular, Roboto, system-ui, 'Segoe UI', Helvetica, Arial, 'Apple Color Emoji', 'Segoe UI Emoji',
      'Segoe UI Symbol', sans-serif;
    font-size: 62.5%;
  }

  body {
    position: relative;
  }

  h1 {
    font-family: SUIT-Bold, sans-serif;
    font-size: 4rem;
  }

  h2 {
    font-family: SUIT-Bold, sans-serif;
    font-size: 3.2rem;
  }

  h3 {
    font-family: SUIT-Bold, sans-serif;
    font-size: 2.8rem;
  }

  h4 {
    font-family: SUIT-Bold, sans-serif;
    font-size: 2.4rem;
  }

  h5 {
    font-family: SUIT-Bold, sans-serif;
    font-size: 2rem;
  }

  button {
    cursor: pointer;
  }

  .pac-icon {
    display: none;
  }

  .pac-container {
    display: flex;
    flex-direction: column;

    padding: 1.6rem;

    border: 1px solid var(--gray-2);
    border-radius: 40px;

    box-shadow: var(--shadow);
  }

  .pac-item {
    width: 100%;
    height: 100%;

    padding: 0.8rem;

    border: none;
    border-radius: 12px;
  }
`;

export default base;
