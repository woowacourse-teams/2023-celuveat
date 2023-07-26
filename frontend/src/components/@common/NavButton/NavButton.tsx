import React, { MouseEvent } from 'react';
import styled, { css } from 'styled-components';

import { FONT_SIZE } from '~/styles/common';

interface NavButtonProps {
  label: string;
  icon: React.ReactNode;
  isShow?: boolean;
  onClick?: (e?: MouseEvent<HTMLElement>) => void;
}

function NavButton({ icon, label, onClick, isShow = false }: NavButtonProps) {
  return (
    <StyledNavButton data-label={label} onClick={onClick} isShow={isShow}>
      <div>{icon}</div>
      <div>
        <span>{label}</span>
      </div>
    </StyledNavButton>
  );
}

export default NavButton;

const StyledNavButton = styled.button<{ isShow: boolean }>`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 0.8rem 0;

  width: 80px;

  border: none;
  background: none;

  font-size: ${FONT_SIZE.sm};

  & > * > * {
    color: ${({ isShow }) => (isShow ? 'var(--black)' : '#717171')};
  }

  &:hover > * > * {
    color: var(--black);
  }

  ${({ isShow }) =>
    isShow
      ? css`
          & > div:last-child {
            position: relative;

            &::after {
              position: absolute;
              top: calc(100% + 12px);
              z-index: -1;

              height: 2px;

              background-color: var(--black);
              white-space: nowrap;
              inset-inline: 0;
              content: '';
            }
          }
        `
      : css`
          &:hover {
            & > div:last-child {
              position: relative;

              &::after {
                position: absolute;
                top: calc(100% + 12px);
                z-index: -1;

                height: 2px;

                background: var(--gray-2);
                white-space: nowrap;
                inset-inline: 0;
                content: '';
              }
            }
          }
        `};
`;

const StyledLabel = styled.div``;
