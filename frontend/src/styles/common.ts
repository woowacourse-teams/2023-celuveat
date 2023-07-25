/* eslint-disable import/prefer-default-export */

import { css } from 'styled-components';

export const FONT_SIZE = {
  xs: '0.8rem',
  sm: '1.2rem',
  md: '1.6rem',
  lg: '2.0rem',
};

export const BORDER_RADIUS = {
  xs: '4px',
  sm: '8px',
  md: '12px',
  lg: '16px',
};

export const truncateText = (numberOfLine: number) =>
  css`
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: ${numberOfLine};

    overflow: hidden;
    text-overflow: ellipsis;
  `;

export const mapUIBase = css`
  display: flex;
  justify-content: center;
  align-items: center;

  border: none;
  border-radius: ${BORDER_RADIUS.sm};
  background-color: var(--white);
  box-shadow: var(--map-shadow);
`;
