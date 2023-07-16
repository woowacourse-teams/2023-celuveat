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
  width: 100%;

  padding: 12px 24px;
  border-bottom: 1px solid var(--gray-1);
`;

const StyledLogo = styled.img`
  width: 136px;
`;
