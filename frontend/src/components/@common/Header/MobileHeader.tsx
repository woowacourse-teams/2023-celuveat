import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { Wrapper } from '@googlemaps/react-wrapper';
import { FONT_SIZE } from '~/styles/common';
import SearchBar from '~/components/SearchBar';
import Logo from '~/assets/icons/logo-icon.svg';

interface MobileHeaderProps {
  showSearchBar?: boolean;
}

function MobileHeader({ showSearchBar = false }: MobileHeaderProps) {
  return (
    <StyledTopNavBar>
      <header>
        <Link aria-label="셀럽잇 홈페이지" role="button" to="/">
          <Logo width={32} />
        </Link>
        <h5>celuveat</h5>
        <div />
      </header>
      {showSearchBar && (
        <Wrapper apiKey={process.env.GOOGLE_MAP_API_KEY} language="ko" libraries={['places']}>
          <SearchBar />
        </Wrapper>
      )}
    </StyledTopNavBar>
  );
}

export default MobileHeader;

const StyledTopNavBar = styled.nav`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  position: fixed;
  z-index: 10;

  width: 100%;
  height: 88px;

  padding: 0.2rem 0.8rem;

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
