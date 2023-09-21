import { styled, css } from 'styled-components';

import { FONT_SIZE } from '~/styles/common';

interface NavItemProps {
  label: string;
  icon: React.ReactNode;
  isShow?: boolean;
}

function NavItem({ icon, label, isShow = false }: NavItemProps) {
  return (
    <StyledNavItem isShow={isShow} aria-selected={isShow}>
      <div>{icon}</div>
      <div>
        <span>{label}</span>
      </div>
    </StyledNavItem>
  );
}

export default NavItem;

const StyledNavItem = styled.div<{ isShow: boolean }>`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  width: 68px;

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

  & > *:first-child > svg {
    fill: ${({ isShow }) => (isShow ? 'var(--black)' : '#717171')};
  }

  &:hover > *:first-child > svg {
    fill: var(--black);
  }

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
              top: calc(100% + 10px);
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
                top: calc(100% + 10px);
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
