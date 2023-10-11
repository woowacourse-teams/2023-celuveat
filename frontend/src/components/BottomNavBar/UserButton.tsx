import { useQuery } from '@tanstack/react-query';
import { styled } from 'styled-components';
import UserIcon from '~/assets/icons/etc/user.svg';
import ProfileImage from '../@common/ProfileImage';
import NavItem from '../@common/NavItem';
import { getProfile } from '~/api/user';
import type { ProfileData } from '~/@types/api.types';
import useAuth from '~/hooks/server/useAuth';
import useNavigateSignUp from '~/hooks/useNavigateSignUp';

function UserButton() {
  const { doLogoutMutation } = useAuth();
  const { goSignUp } = useNavigateSignUp();

  const { data: profile, isSuccess: isLogin } = useQuery<ProfileData>({
    queryKey: ['profile'],
    queryFn: getProfile,
  });

  const clickLoginNavItem = () => {
    if (!isLogin) {
      goSignUp();
      return;
    }

    doLogoutMutation(profile.oauthServer);
  };

  return (
    <StyledNavBarButton type="button" onClick={clickLoginNavItem}>
      {isLogin ? (
        <NavItem icon={<ProfileImage name={profile.nickname} imageUrl={profile.profileImageUrl} size="24px" />} />
      ) : (
        <NavItem icon={<UserIcon height={28} />} />
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
