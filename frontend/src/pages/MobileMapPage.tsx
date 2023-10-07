/* eslint-disable @typescript-eslint/ban-ts-comment */
import { styled } from 'styled-components';
import { shallow } from 'zustand/shallow';
import { useQuery } from '@tanstack/react-query';
import useBooleanState from '~/hooks/useBooleanState';
import MapIcon from '~/assets/icons/map.svg';
import ListIcon from '~/assets/icons/list.svg';
import Down from '~/assets/icons/down.svg';

import Map from '~/components/@common/Map';
import MiniRestaurantCard from '~/components/MiniRestaurantCard';
import useMapState from '~/hooks/store/useMapState';
import useRestaurantsQueryStringState from '~/hooks/store/useRestaurantsQueryStringState';
import { RestaurantListData } from '~/@types/api.types';
import { getRestaurants } from '~/api/restaurant';

function MobileMapPage() {
  const { value: isListShowed, toggle: toggleShowedList } = useBooleanState(false);
  const [preview, setPreview] = useMapState(state => [state.preview, state.setPreview]);
  const [boundary, celebId, currentPage, restaurantCategory, sort] = useRestaurantsQueryStringState(
    state => [state.boundary, state.celebId, state.currentPage, state.restaurantCategory, state.sort],
    shallow,
  );
  const { data: restaurantDataList } = useQuery<RestaurantListData>({
    queryKey: ['restaurants', boundary, celebId, restaurantCategory, currentPage, sort],
    queryFn: () => getRestaurants({ boundary, celebId, sort, category: restaurantCategory, page: currentPage }),
    keepPreviousData: true,
  });

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
    <StyledMobileLayout>
      <StyledRestaurantCardContainer isListShowed={isListShowed}>
        {restaurantDataList?.content.map(({ celebs, ...restaurant }) => (
          <MiniRestaurantCard restaurant={restaurant} celebs={celebs} showWaterMark={false} />
        ))}
      </StyledRestaurantCardContainer>
      <StyledMapContainer isListShowed={isListShowed}>
        <Map />
      </StyledMapContainer>
      <StyledModal>
        {isListShowed ? (
          <StyledToggleButton type="button" onClick={toggleShowedList}>
            <span>지도</span>
            <MapIcon width={24} />
          </StyledToggleButton>
        ) : (
          <StyledToggleButton type="button" onClick={toggleShowedList}>
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

const StyledMobileLayout = styled.main`
  position: relative;

  width: 100%;
  height: calc(100% - 48px);
`;

const StyledRestaurantCardContainer = styled.div<{ isListShowed: boolean }>`
  display: ${({ isListShowed }) => (isListShowed ? 'flex' : 'none')};
  flex-direction: column;
  gap: 1.6rem;

  position: absolute;
  top: 0;
  z-index: 1;

  width: 100%;
  height: 100vh;

  padding: 1.2rem 0.8rem;

  background-color: var(--white);
`;

const StyledMapContainer = styled.div<{ isListShowed: boolean }>`
  display: ${({ isListShowed }) => isListShowed && 'none'};

  width: 100%;
  height: 100%;
`;

const StyledToggleButton = styled.button`
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
