import { Link, useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { useQuery } from '@tanstack/react-query';
import { useEffect } from 'react';
import type { ProfileData } from '~/@types/api.types';
import Footer from '~/components/@common/Footer';
import ProfileImage from '~/components/@common/ProfileImage';
import useAuth from '~/hooks/server/useAuth';
import { BORDER_RADIUS, FONT_SIZE } from '~/styles/common';
import { getProfile } from '~/api/user';
import SignUpPage from './SignUpPage';
import useBottomNavBarState from '~/hooks/store/useBottomNavBarState';

function UserPage() {
  const navigator = useNavigate();
  const { doLogoutMutation } = useAuth();
  const setUserSelected = useBottomNavBarState(state => state.setUserSelected);

  const { data: profile, isSuccess: isLogin } = useQuery<ProfileData>({
    queryKey: ['profile'],
    queryFn: () => getProfile(),
  });

  const clickWishListService = () => {
    navigator('/restaurants/like');
  };

  const logOut = () => {
    doLogoutMutation(profile.oauthServer);
    navigator('/');
  };

  useEffect(() => {
    setUserSelected();
  }, []);

  if (isLogin)
    return (
      <StyledUserAndFooterDivider>
        <StyledUserContainer>
          <h4>프로필</h4>
          <StyledProfileSection>
            <ProfileImage name={profile.nickname} imageUrl={profile.profileImageUrl} size="120px" />
            <h5>{profile.nickname}</h5>
            <div>{profile.oauthServer} 계정입니다.</div>
          </StyledProfileSection>
          <StyledLine />
          <StyledServiceSection>
            <h4>서비스</h4>
            <button type="button" onClick={clickWishListService}>
              위시리스트
            </button>
          </StyledServiceSection>
          <StyledLine />
          <StyledSignSection>
            <StyledLogoutButton type="button" onClick={logOut}>
              로그아웃
            </StyledLogoutButton>
            <Link to="/withdrawal">
              <StyledWithDrawal>회원탈퇴</StyledWithDrawal>
            </Link>
          </StyledSignSection>
        </StyledUserContainer>
        <Footer />
      </StyledUserAndFooterDivider>
    );

  return <SignUpPage />;
}

export default UserPage;

const StyledUserAndFooterDivider = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;

  width: 100vw;
  height: calc(100dvh - 88px);

  padding-bottom: 4.4rem;
`;

const StyledUserContainer = styled.main`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;

  width: 100%;

  padding: 1.2rem;
`;

const StyledLine = styled.div`
  width: 100%;
  height: 1px;
  border-bottom: 1px solid var(--gray-2);
`;

const StyledProfileSection = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.8rem;

  width: 100%;

  & > div {
    font-size: ${FONT_SIZE.sm};
  }
`;

const StyledServiceSection = styled.section`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;

  & > button {
    width: 100%;

    padding: 1.2rem;

    border: none;
    background: transparent;

    font-size: ${FONT_SIZE.md};
    text-align: left;

    &:hover {
      background: var(--gray-1);
    }
  }
`;

const StyledSignSection = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.8rem;

  width: 100%;
`;

const StyledLogoutButton = styled.button`
  width: 100%;

  padding: 1.6rem 0;

  border: 1px solid var(--black);
  border-radius: ${BORDER_RADIUS.sm};
  background: none;

  font-size: ${FONT_SIZE.md};
  text-align: center;

  &:hover {
    background: var(--gray-1);
  }
`;

const StyledWithDrawal = styled.a`
  border: none;
  background-color: transparent;

  color: var(--gray-3);
  font-size: ${FONT_SIZE.sm};
`;
