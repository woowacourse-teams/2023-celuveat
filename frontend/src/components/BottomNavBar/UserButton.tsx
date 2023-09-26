import { useQuery } from '@tanstack/react-query';
import { useLocation, useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';
import UserIcon from '~/assets/icons/etc/user.svg';
import ProfileImage from '../@common/ProfileImage';
import NavItem from '../@common/NavItem';
import { getLogout, getProfile } from '~/api/user';
import type { ProfileData } from '~/@types/api.types';

function UserButton() {
  const navigator = useNavigate();
  const location = useLocation();
  const { data: profile, isSuccess: isLogin } = useQuery<ProfileData>({
    queryKey: ['profile'],
    queryFn: () => getProfile(),
  });

  const clickLogin = () => navigator('/signUp', { state: { from: location.pathname } });

  const clickLogout = () => {
    getLogout(profile.oauthServer);
    window.location.href = '/';
  };

  const clickLoginNavItem = () => {
    if (!isLogin) {
      clickLogin();
      return;
    }

    clickLogout();
  };

  return (
    <StyledNavBarButton type="button" onClick={clickLoginNavItem}>
      {isLogin ? (
        <NavItem
          label="로그아웃"
          icon={<ProfileImage name={profile.nickname} imageUrl={profile.profileImageUrl} size="24px" />}
        />
      ) : (
        <NavItem label="로그인" icon={<UserIcon width={24} />} />
      )}
    </StyledNavBarButton>
  );
}

export default UserButton;

const StyledNavBarButton = styled.button`
  border: none;
  outline: none;

  background: none;
`;
