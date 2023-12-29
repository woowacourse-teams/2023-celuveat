import { styled } from 'styled-components';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Wrapper } from '@googlemaps/react-wrapper';

import Logo from '~/assets/icons/logo.svg';
import SearchBar from '~/components/SearchBar';

import TextButton from '~/components/@common/Button';
import InfoDropDown from '~/components/InfoDropDown/InfoDropDown';

function Header() {
  const navigator = useNavigate();
  const { pathname } = useLocation();

  const isMapPage = pathname === '/map';

  return (
    <StyledHeader>
      <Link aria-label="셀럽잇 홈페이지" role="button" to="/">
        <Logo width={136} />
      </Link>
      {isMapPage && (
        <Wrapper apiKey={process.env.GOOGLE_MAP_API_KEY} language="ko" libraries={['places']}>
          <SearchBar />
        </Wrapper>
      )}
      <StyledRightSection>
        {!isMapPage && (
          <TextButton text="지도로 보기" colorType="dark" type="button" onClick={() => navigator('/map')} />
        )}
        <InfoDropDown />
      </StyledRightSection>
    </StyledHeader>
  );
}

export default Header;

const StyledRightSection = styled.section`
  display: flex;
  gap: 2.4rem;
`;

const StyledHeader = styled.header`
  display: flex;
  justify-content: space-between;
  align-items: center;

  position: relative;

  width: 100%;
  height: 80px;

  padding: 1.2rem 2.4rem;

  background-color: var(--white);
  border-bottom: 1px solid var(--gray-1);
`;
