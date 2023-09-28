import { styled } from 'styled-components';
import { Link, useLocation } from 'react-router-dom';
import { Wrapper } from '@googlemaps/react-wrapper';
import { FONT_SIZE } from '~/styles/common';
import SearchBar from '~/components/SearchBar';
import Logo from '~/assets/icons/logo-icon.svg';

function MobileHeader() {
  const { pathname } = useLocation();

  const isHome = pathname === '/map';

  return (
    <StyledTopNavBar isHome={isHome}>
      <header>
        <Link aria-label="셀럽잇 홈페이지" role="button" to="/">
          <Logo width={32} />
        </Link>
        <h5>celuveat</h5>
        <div />
      </header>
      {isHome && (
        <Wrapper apiKey={process.env.GOOGLE_MAP_API_KEY} language="ko" libraries={['places']}>
          <SearchBar />
        </Wrapper>
      )}
    </StyledTopNavBar>
  );
}

export default MobileHeader;

const StyledTopNavBar = styled.nav<{ isHome: boolean }>`
  display: flex;
  flex-direction: column;
  align-items: center;

  position: fixed;
  z-index: 100;

  width: 100%;
  height: ${({ isHome }) => (isHome ? '88px' : '44px')};

  padding: 0 0.8rem;

  background-color: var(--white);
  box-shadow: var(--map-shadow);

  & > header {
    display: flex;
    justify-content: space-between;
    align-items: center;

    width: 100%;
    height: 44px;

    & > div {
      width: 32px;

      font-size: ${FONT_SIZE.lg};
    }
  }
`;
