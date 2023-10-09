import { Link, useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import type { ProfileData } from '~/@types/api.types';
import Footer from '~/components/@common/Footer';
import ProfileImage from '~/components/@common/ProfileImage';
import useAuth from '~/hooks/server/useAuth';
import useBottomNavBarState from '~/hooks/store/useBottomNavBarState';
import { BORDER_RADIUS, FONT_SIZE } from '~/styles/common';

interface UserPageProps {
  profile: ProfileData;
}

function UserPage({ profile }: UserPageProps) {
  const navigator = useNavigate();
  const { doLogoutMutation } = useAuth();
  const setHomeSelected = useBottomNavBarState(state => state.setHomeSelected);

  const navigateWishList = () => navigator('/restaurants/like');

  const logOut = () => {
    doLogoutMutation(profile.oauthServer);
    setHomeSelected();
  };

  return (
    <StyledUserAndFooterDivider>
      <StyledUserContainer>
        <h2>프로필</h2>
        <StyledProfileSection>
          <ProfileImage name={profile.nickname} imageUrl={profile.profileImageUrl} size="120px" />
          <h3>{profile.nickname}</h3>
          <div>{profile.oauthServer} 계정입니다.</div>
        </StyledProfileSection>
        <StyledLine />
        <StyledServiceSection>
          <h3>서비스</h3>
          <button type="button" onClick={navigateWishList}>
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
}

export default UserPage;

const StyledUserAndFooterDivider = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;

  width: 100vw;
  height: calc(100vh - 92px);
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
