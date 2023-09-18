/* eslint-disable @typescript-eslint/ban-ts-comment */
import { useRef } from 'react';
import { styled, css } from 'styled-components';
import useBooleanState from '~/hooks/useBooleanState';
import useScrollDirection from '~/hooks/useScrollDirection';
import useScrollBlock from '~/hooks/useScrollBlock';
import useScrollEnd from '~/hooks/useScrollEnd';
import MapIcon from '~/assets/icons/map.svg';
import ListIcon from '~/assets/icons/list.svg';

import RestaurantCardList from '~/components/RestaurantCardList';
import Footer from '~/components/@common/Footer';
import Map from '~/components/@common/Map';
import MobileHeader from '~/components/@common/Header/MobileHeader';
import BottomNavBar from '~/components/BottomNavBar/BottomNavBar';

function MobileMainPage() {
  const ref = useRef();
  const scrollDirection = useScrollDirection();
  const { isEnd } = useScrollEnd({ direction: 'Y', threshold: 200 });
  const { value: isListShowed, toggle: toggleShowedList } = useBooleanState(false);

  useScrollBlock(ref);

  return (
    <StyledMobileMainPageContainer>
      <MobileHeader showSearchBar />

      {isListShowed ? (
        <StyledToggleButton
          type="button"
          onClick={toggleShowedList}
          isHide={isEnd}
          isNavBarHide={isListShowed && scrollDirection.y === 'down'}
          ref={ref}
        >
          <span>지도</span>
          <MapIcon width={24} />
        </StyledToggleButton>
      ) : (
        <StyledToggleButton type="button" onClick={toggleShowedList} isHide={false} isNavBarHide={false} ref={ref}>
          <span>리스트</span>
          <ListIcon width={20} stroke="#fff" />
        </StyledToggleButton>
      )}

      <BottomNavBar isHide={isListShowed && scrollDirection.y === 'down'} />

      <StyledMobileLayout isListShowed={isListShowed}>
        <div>
          <RestaurantCardList />
          <Footer />
        </div>
        <Map />
      </StyledMobileLayout>
    </StyledMobileMainPageContainer>
  );
}

export default MobileMainPage;

const StyledMobileMainPageContainer = styled.div`
  width: 100%;
  height: 100vh;
`;

const StyledMobileLayout = styled.main<{ isListShowed: boolean }>`
  position: absolute;
  top: 88px;

  width: 100vw;
  height: calc(100vh - 88px - 64px);

  & > div:first-child {
    position: absolute;
    top: 0;
    z-index: 1;

    width: 100%;
    height: 100vh;

    background-color: var(--white);

    ${({ isListShowed }) => !isListShowed && 'display: none;'}

    & > *:first-child {
      min-height: calc(100vh - 88px);
    }
  }
`;

const StyledToggleButton = styled.button<{ isHide: boolean; isNavBarHide: boolean }>`
  display: flex;
  justify-content: center;
  align-items: center;

  position: fixed;
  bottom: 88px;

  gap: 0.8rem;

  width: 100px;

  z-index: 20;
  left: calc(50% - 50px);

  height: 40px;

  border: none;
  border-radius: 50px;
  background-color: var(--black);

  transition: 0.2s ease-in-out;

  & > * {
    color: var(--white);
  }

  &:hover {
    scale: 1.04;
    box-shadow: var(--map-shadow);
  }

  ${({ isNavBarHide }) =>
    isNavBarHide &&
    css`
      transition: 0.4s ease-in-out;
      transform: translateY(64px);
    `}

  ${({ isHide }) =>
    isHide &&
    css`
      opacity: 0;
      transition: 0.4s ease-in-out;
      transform: translateY(64px);
    `}
`;
