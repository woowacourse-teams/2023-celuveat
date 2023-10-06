import { useRef, useState } from 'react';
import { styled } from 'styled-components';
import { useLocation, useNavigate } from 'react-router-dom';
import HomeIcon from '~/assets/icons/home.svg';
import SignInIcon from '~/assets/icons/sign-in.svg';
import MapIcon from '~/assets/icons/navmap.svg';
import useScrollBlock from '~/hooks/useScrollBlock';

interface BottomNavBarProps {
  isHide: boolean;
}

type BottomIcons = 'home' | 'map' | 'user';

function BottomNavBar({ isHide }: BottomNavBarProps) {
  const ref = useRef();
  const navigator = useNavigate();
  const { pathname } = useLocation();
  const [clickedIcon, setClickedIcon] = useState<BottomIcons>('home');

  const clickHome = () => {
    setClickedIcon('home');
    navigator('/');
  };
  const clickMap = () => {
    setClickedIcon('map');
    navigator('/map');
  };
  const clickLogin = () => {
    setClickedIcon('user');
    navigator('/signUp', { state: { from: pathname } });
  };

  useScrollBlock(ref);

  return (
    <StyledBottomNavBar isHide={isHide} ref={ref}>
      <StyledNavBarButton onClick={clickHome}>
        <HomeIcon fill={clickedIcon === 'home' ? '#000' : 'none'} />
      </StyledNavBarButton>
      <StyledNavBarButton onClick={clickMap}>
        <MapIcon strokeWidth={clickedIcon === 'map' ? 2 : 1.2} />
      </StyledNavBarButton>
      <StyledNavBarButton onClick={clickLogin}>
        <SignInIcon fill={clickedIcon === 'user' ? '#000' : 'none'} />
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
