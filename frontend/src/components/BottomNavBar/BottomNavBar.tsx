import { useRef } from 'react';
import { styled } from 'styled-components';
import { useNavigate } from 'react-router-dom';
import WishListIcon from '~/assets/icons/black-love.svg';
import HomeIcon from '~/assets/icons/home.svg';
import UserIcon from '~/assets/icons/user-vector.svg';
import MapIcon from '~/assets/icons/navmap.svg';
import useScrollBlock from '~/hooks/useScrollBlock';

interface BottomNavBarProps {
  isHide: boolean;
}

function BottomNavBar({ isHide }: BottomNavBarProps) {
  const ref = useRef();
  const navigator = useNavigate();

  const clickHome = () => navigator('/');
  const clickMap = () => navigator('/map');
  const clickLogin = () => navigator('/signup');
  const clickWishList = () => navigator('/restaurants/like');

  useScrollBlock(ref);

  return (
    <StyledBottomNavBar isHide={isHide} ref={ref}>
      <StyledNavBarButton onClick={clickHome}>
        <HomeIcon />
      </StyledNavBarButton>
      {/* <StyledNavBarButton onClick={clickWishList}>
        <WishListIcon />
      </StyledNavBarButton> */}
      <StyledNavBarButton onClick={clickMap}>
        <MapIcon />
      </StyledNavBarButton>
      <StyledNavBarButton onClick={clickLogin}>
        <UserIcon />
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
