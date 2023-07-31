import styled, { css } from 'styled-components';

import Menu from '~/assets/icons/etc/menu.svg';
import User from '~/assets/icons/etc/user.svg';

interface InfoButtonProps {
  isShow?: boolean;
}

function InfoButton({ isShow = false }: InfoButtonProps) {
  return (
    <StyledInfoButton isShow={isShow}>
      <Menu />
      <User />
    </StyledInfoButton>
  );
}

export default InfoButton;

const StyledInfoButton = styled.button<InfoButtonProps>`
  display: flex;
  justify-content: space-between;
  align-items: center;

  width: 77px;

  padding: 0.5rem 0.5rem 0.5rem 1.2rem;

  border: 1px solid #ddd;
  border-radius: 21px;
  background: transparent;

  cursor: pointer;

  ${({ isShow }) =>
    isShow &&
    css`
      box-shadow: var(--shadow);
    `}

  &:hover {
    box-shadow: var(--shadow);

    transition: box-shadow 0.2s ease-in-out;
  }
`;
