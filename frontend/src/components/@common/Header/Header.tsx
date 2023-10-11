import { styled } from 'styled-components';
import { useQuery } from '@tanstack/react-query';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Wrapper } from '@googlemaps/react-wrapper';

import Logo from '~/assets/icons/logo.svg';

import InfoDropDown from '~/components/InfoDropDown';
import LoginModal from '~/components/LoginModal';
import SearchBar from '~/components/SearchBar';

import useAuth from '~/hooks/server/useAuth';
import useBooleanState from '~/hooks/useBooleanState';

import { getProfile } from '~/api/user';

import type { ProfileData } from '~/@types/api.types';

function Header() {
  const { data: profileData } = useQuery<ProfileData>({
    queryKey: ['profile'],
    queryFn: getProfile,
    retry: 1,
  });

  const navigator = useNavigate();
  const { pathname } = useLocation();
  const { value: isModalOpen, setTrue: openModal, setFalse: closeModal } = useBooleanState(false);
  const { doLogoutMutation } = useAuth();

  const handleInfoDropDown = (event: React.MouseEvent<HTMLElement>) => {
    const currentOption = event.currentTarget.dataset.name;

    if (currentOption === '로그인') openModal();
    if (currentOption === '위시리스트') navigator('/restaurants/like');
    if (currentOption === '회원 탈퇴') navigator('/withdrawal');
    if (currentOption === '로그아웃') {
      doLogoutMutation(profileData.oauthServer);
    }
  };

  const isHome = pathname === '/';

  return (
    <>
      <StyledHeader>
        <Link aria-label="셀럽잇 홈페이지" role="button" to="/">
          <Logo width={136} />
        </Link>
        {isHome && (
          <Wrapper apiKey={process.env.GOOGLE_MAP_API_KEY} language="ko" libraries={['places']}>
            <SearchBar />
          </Wrapper>
        )}

        <InfoDropDown externalOnClick={handleInfoDropDown} isOpen={isModalOpen} label="로그인" />
      </StyledHeader>

      <LoginModal close={closeModal} isOpen={isModalOpen} />
    </>
  );
}

export default Header;

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
