/* eslint-disable import/prefer-default-export */
import { keyframes, css } from 'styled-components';

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

const colorChange = keyframes`
  0% {
    background-color: var(--gray-1);
  }
  100% {
    background-color: var(--gray-2);
  }
`;

export const paintSkeleton = css`
  animation: ${colorChange} 1s ease-in-out infinite alternate;

  border-radius: ${BORDER_RADIUS.md};
`;
