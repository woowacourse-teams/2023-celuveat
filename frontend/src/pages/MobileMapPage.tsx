/* eslint-disable @typescript-eslint/ban-ts-comment */
import { useRef } from 'react';
import { styled, css } from 'styled-components';
import useBooleanState from '~/hooks/useBooleanState';
import useScrollDirection from '~/hooks/useScrollDirection';
import useScrollBlock from '~/hooks/useScrollBlock';
import useScrollEnd from '~/hooks/useScrollEnd';
import MapIcon from '~/assets/icons/map.svg';
import ListIcon from '~/assets/icons/list.svg';
import Down from '~/assets/icons/down.svg';

import RestaurantCardList from '~/components/RestaurantCardList';
import Footer from '~/components/@common/Footer';
import Map from '~/components/@common/Map';
import MiniRestaurantCard from '~/components/MiniRestaurantCard';
import useMapState from '~/hooks/store/useMapState';

function MobileMapPage() {
  const ref = useRef();
  const scrollDirection = useScrollDirection();
  const { isEnd } = useScrollEnd({ direction: 'Y', threshold: 200 });
  const { value: isListShowed, toggle: toggleShowedList } = useBooleanState(false);
  const [preview, setPreview] = useMapState(state => [state.preview, state.setPreview]);

  useScrollBlock(ref);

  const getPreview = () => {
    const { celebs, ...restaurant } = preview;

    return (
      <StyledPreview>
        <MiniRestaurantCard restaurant={restaurant} celebs={celebs} showWaterMark={false} />
        <StyledShutDownButton type="button" onClick={() => setPreview(null)}>
          <Down />
        </StyledShutDownButton>
      </StyledPreview>
    );
  };

  return (
    <StyledMobileLayout isListShowed={isListShowed}>
      <div>
        <RestaurantCardList />
        <Footer />
      </div>
      <Map />
      <StyledModal>
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
        {preview && !isListShowed && getPreview()}
      </StyledModal>
    </StyledMobileLayout>
  );
}

export default MobileMapPage;

const StyledMobileLayout = styled.main<{ isListShowed: boolean }>`
  position: relative;

  width: 100%;
  height: calc(100% - 48px);

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
  gap: 0.8rem;

  z-index: 20;

  width: 100px;
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

const StyledModal = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.8rem;

  position: fixed;
  bottom: 56px;
  z-index: 1;

  width: 100%;
`;

const StyledPreview = styled.div`
  position: relative;

  width: 90%;

  padding: 1.2rem 0.8rem;

  border-radius: 12px;
  background-color: white;
`;

const StyledShutDownButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;

  position: absolute;
  right: 12px;
  bottom: 12px;

  width: 32px;
  height: 32px;

  border: none;
  border-radius: 50%;
  background-color: var(--gray-1);
`;
