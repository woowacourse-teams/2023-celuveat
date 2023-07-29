import styled from 'styled-components';

import Menu from '~/assets/icons/etc/menu.svg';
import User from '~/assets/icons/etc/user.svg';

function InfoButton() {
  return (
    <StyledInfoButton>
      <Menu />
      <User />
    </StyledInfoButton>
  );
}

export default InfoButton;

const StyledInfoButton = styled.button`
  display: flex;
  justify-content: space-between;
  align-items: center;

  width: 77px;

  padding: 0.5rem 0.5rem 0.5rem 1.2rem;

  border: 1px solid #ddd;
  border-radius: 21px;
  background: transparent;

  cursor: pointer;

  &:hover {
    box-shadow: 0 1px 2px rgb(0 0 0 / 15%);

    transition: box-shadow 0.2s ease-in-out;
  }
`;
