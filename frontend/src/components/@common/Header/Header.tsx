import { styled } from 'styled-components';
import Logo from '~/assets/logo.png';

function Header() {
  return (
    <StyledHeader>
      <StyledLogo alt="셀럽잇 로고" src={Logo} />
    </StyledHeader>
  );
}

export default Header;

const StyledHeader = styled.header`
  display: flex;
  align-items: center;

  position: sticky;
  top: 0;
  z-index: 10;

  width: 100%;
  height: 80px;

  padding: 1.2rem 2.4rem;

  background-color: var(--white);
  border-bottom: 1px solid var(--gray-1);
`;

const StyledLogo = styled.img`
  width: 136px;
`;
