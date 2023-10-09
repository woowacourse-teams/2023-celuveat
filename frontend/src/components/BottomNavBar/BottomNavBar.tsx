import { useRef, useState } from 'react';
import { styled } from 'styled-components';
import { useLocation, useNavigate } from 'react-router-dom';
import { shallow } from 'zustand/shallow';
import HomeIcon from '~/assets/icons/home.svg';
import SignInIcon from '~/assets/icons/sign-in.svg';
import MapIcon from '~/assets/icons/navmap.svg';
import useScrollBlock from '~/hooks/useScrollBlock';
import useBottomNavBarState from '~/hooks/store/useBottomNavBarState';

interface BottomNavBarProps {
  isHide: boolean;
}

function BottomNavBar({ isHide }: BottomNavBarProps) {
  const ref = useRef();
  const navigator = useNavigate();
  const { pathname } = useLocation();
  const [selected, setHomeSelected, setMapSelected, setUserSelected] = useBottomNavBarState(
    state => [state.selected, state.setHomeSelected, state.setMapSelected, state.setUserSelected],
    shallow,
  );

  const clickHome = () => {
    setHomeSelected();
    navigator('/');
  };
  const clickMap = () => {
    setMapSelected();
    navigator('/map');
  };
  const clickLogin = () => {
    setUserSelected();
    navigator('/signUp', { state: { from: pathname } });
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
      <StyledNavBarButton onClick={clickLogin}>
        <SignInIcon fill={selected === 'user' ? '#000' : 'none'} />
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
