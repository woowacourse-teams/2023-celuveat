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
    <StyledNavButton onClick={onClick} isShow={isShow}>
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
