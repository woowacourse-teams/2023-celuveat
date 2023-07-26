import React from 'react';
import styled, { css } from 'styled-components';

import { FONT_SIZE } from '~/styles/common';

interface NavButtonProps {
  label: string;
  icon: React.ReactNode;
  isShow?: boolean;
  onClick?: (e?: React.MouseEvent<HTMLElement>) => void;
  onBlur?: (e?: React.FocusEvent<HTMLElement>) => void;
}

function NavButton({ icon, label, isShow = false, ...props }: NavButtonProps) {
  return (
    <StyledNavButton data-label={label} isShow={isShow} {...props}>
      <div>{icon}</div>
      <div>{label}</div>
    </StyledNavButton>
  );
}

export default NavButton;

const StyledNavButton = styled.button<{ isShow: boolean }>`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.6rem;

  width: fit-content;
  height: 56px;

  margin: 1.6rem 0 1rem;

  border: none;
  background: var(--white);
  outline: none;

  font-size: ${FONT_SIZE.sm};

  ${({ isShow }) =>
    isShow &&
    css`
      border-bottom: 3px solid var(--black);
    `};
`;
