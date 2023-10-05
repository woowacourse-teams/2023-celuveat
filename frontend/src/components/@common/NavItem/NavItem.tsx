import { styled, css } from 'styled-components';

import { FONT_SIZE } from '~/styles/common';

interface NavItemProps {
  label?: string;
  icon: React.ReactNode;
  isShow?: boolean;
  isInteractive?: boolean;
}

function NavItem({ icon, label, isShow = false, isInteractive = false }: NavItemProps) {
  return (
    <StyledNavItem isShow={isShow} aria-selected={isShow} isInteractive={isInteractive}>
      <div>{icon}</div>
      {label && (
        <div>
          <span>{label}</span>
        </div>
      )}
    </StyledNavItem>
  );
}

export default NavItem;

const StyledNavItem = styled.div<{ isShow: boolean; isInteractive: boolean }>`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 0.8rem;

  width: 68px;

  padding: 0.4rem;

  border: none;
  background: none;

  font-size: ${FONT_SIZE.sm};

  & > div:first-child {
    display: flex;
    justify-content: center;
    align-items: center;

    width: 40px;
    height: 40px;
  }

  ${({ isInteractive }) =>
    isInteractive &&
    css`
      &:hover {
        border-radius: 12px;
        background-color: var(--gray-1);
      }
    `}

  ${({ isInteractive, isShow }) =>
    isInteractive &&
    isShow &&
    css`
      border-radius: 12px;
      background-color: var(--gray-1);
    `};
`;
