import { useRef } from 'react';
import { styled } from 'styled-components';
import { useLocation, useNavigate } from 'react-router-dom';
import { shallow } from 'zustand/shallow';
import { useQuery } from '@tanstack/react-query';
import HomeIcon from '~/assets/icons/home.svg';
import UserIcon from '~/assets/icons/user.svg';
import MapIcon from '~/assets/icons/navmap.svg';
import HeartIcon from '~/assets/icons/navbar-heart.svg';
import useScrollBlock from '~/hooks/useScrollBlock';
import useBottomNavBarState from '~/hooks/store/useBottomNavBarState';
import { ProfileData } from '~/@types/api.types';
import { getProfile } from '~/api/user';

interface BottomNavBarProps {
  isHide: boolean;
}

function BottomNavBar({ isHide }: BottomNavBarProps) {
  const ref = useRef();
  const navigator = useNavigate();
  const { pathname } = useLocation();
  const [selected, setHomeSelected, setMapSelected, setUserSelected, setWishListSelected] = useBottomNavBarState(
    state => [
      state.selected,
      state.setHomeSelected,
      state.setMapSelected,
      state.setUserSelected,
      state.setWishListSelected,
    ],
    shallow,
  );

  const { isSuccess: isLogin } = useQuery<ProfileData>({
    queryKey: ['profile'],
    queryFn: () => getProfile(),
  });

  const clickHome = () => {
    setHomeSelected();
    navigator('/');
  };
  const clickMap = () => {
    setMapSelected();
    navigator('/map');
  };
  const clickWishList = () => {
    setWishListSelected();
    navigator('/restaurants/like');
  };
  const clickUser = () => {
    setUserSelected();

    if (isLogin) navigator('/user');
    else navigator('/signUp', { state: { from: pathname } });
  };

  useScrollBlock(ref);

  return (
    <StyledBottomNavBar isHide={isHide} ref={ref}>
      <StyledNavBarButton onClick={clickHome}>
        <HomeIcon fill={selected === 'home' ? '#000' : 'none'} />
      </StyledNavBarButton>
      <StyledNavBarButton onClick={clickMap}>
        <MapIcon strokeWidth={selected === 'map' ? 2 : 1.2} />
      </StyledNavBarButton>
      <StyledNavBarButton onClick={clickWishList}>
        <HeartIcon fill={selected === 'wishList' ? '#000' : 'none'} />
      </StyledNavBarButton>
      <StyledNavBarButton onClick={clickUser}>
        <UserIcon fill={selected === 'user' ? '#000' : 'none'} />
      </StyledNavBarButton>
    </StyledBottomNavBar>
  );
}

export default BottomNavBar;

const StyledBottomNavBar = styled.nav<{ isHide: boolean }>`
  display: flex;
  justify-content: space-evenly;
  align-items: center;

  position: fixed;
  bottom: 0;
  z-index: 20;

  width: 100vw;
  height: 48px;
  min-height: 48px;

  background-color: var(--white);

  border-top: 1px solid var(--gray-1);

  ${({ isHide }) => isHide && 'transform: translateY(64px)'};

  transition: 0.4s ease-in-out;
`;

const StyledNavBarButton = styled.div`
  display: flex;
  justify-content: center;

  width: 28px;
  max-width: 28px;
`;
