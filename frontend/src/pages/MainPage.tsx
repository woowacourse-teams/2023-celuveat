import { useState } from 'react';
import { styled, css } from 'styled-components';
import { useQuery } from '@tanstack/react-query';
import Footer from '~/components/@common/Footer';
import Header from '~/components/@common/Header';
import Map from '~/components/@common/Map';
import CategoryNavbar from '~/components/CategoryNavbar';
import RESTAURANT_CATEGORY from '~/constants/restaurantCategory';
import useMediaQuery from '~/hooks/useMediaQuery';
import BottomSheet from '~/components/@common/BottomSheet';
import RestaurantCardList from '~/components/RestaurantCardList';
import { getCelebs, getRestaurants } from '~/api';

import type { Celeb } from '~/@types/celeb.types';
import type { RestaurantCategory } from '~/@types/restaurant.types';
import type { RestaurantListData } from '~/@types/api.types';
import useBottomSheetStatus from '~/hooks/store/useBottomSheetStatus';
import useMapState from '~/hooks/store/useMapState';
import CelebNavbar from '~/components/CelebNavbar';

function MainPage() {
  const isBottomSheetOpen = useBottomSheetStatus(state => state.isOpen);
  const { isMobile } = useMediaQuery();
  const [isMapExpanded, setIsMapExpanded] = useState(false);
  const [celebId, setCelebId] = useState<Celeb['id']>(-1);
  const [currentPage, setCurrentPage] = useState(0);
  const [restaurantCategory, setRestaurantCategory] = useState<RestaurantCategory>('전체');
  const [hoveredId, setHoveredId] = useState<number | null>(null);
  const boundary = useMapState(state => state.boundary);

  const {
    data: restaurantListData,
    isLoading,
    refetch,
  } = useQuery<RestaurantListData>({
    queryKey: ['restaurants', boundary, celebId, restaurantCategory, currentPage],
    queryFn: () => getRestaurants({ boundary, celebId, category: restaurantCategory, page: currentPage }),
  });

  const { data: celebs, isSuccess: isSuccessCelebs } = useQuery({
    queryKey: ['celebs'],
    queryFn: () => getCelebs(),
  });

  const clickRestaurantCategory = (e: React.MouseEvent<HTMLElement>) => {
    const currentCategory = e.currentTarget.dataset.label as RestaurantCategory;

    setRestaurantCategory(currentCategory);
    setCurrentPage(0);
    refetch();
  };

  const clickCeleb = (e: React.MouseEvent<HTMLElement>) => {
    const currentCelebId = Number(e.currentTarget.dataset.label);

    setCelebId(currentCelebId);
    setCurrentPage(0);
    refetch();
  };

  const toggleMapExpand = () => setIsMapExpanded(prev => !prev);
  const bottomSheetHeader = (total: number) => `지도 영역에 있는 음식점 수 ${total}개`;

  return (
    <>
      <Header />
      <StyledNavbarSection>
        {isSuccessCelebs && <CelebNavbar celebs={celebs} externalOnClick={clickCeleb} />}
        <CategoryNavbar categories={RESTAURANT_CATEGORY} externalOnClick={clickRestaurantCategory} />
      </StyledNavbarSection>
      {isMobile ? (
        <StyledMobileLayout>
          <StyledLayer isMobile={isMobile}>
            <Map
              setCurrentPage={setCurrentPage}
              data={restaurantListData?.content}
              toggleMapExpand={toggleMapExpand}
              hoveredId={hoveredId}
              loadingData={isLoading}
            />
            <StyledMapBottomCover isBottomSheetOpen={isBottomSheetOpen} />
          </StyledLayer>
          <BottomSheet title={bottomSheetHeader(restaurantListData?.totalElementsCount)} isLoading={isLoading}>
            <RestaurantCardList
              restaurantDataList={restaurantListData}
              loading={isLoading}
              setHoveredId={setHoveredId}
              setCurrentPage={setCurrentPage}
            />
          </BottomSheet>
        </StyledMobileLayout>
      ) : (
        <>
          <StyledLayout isMapExpanded={isMapExpanded}>
            <StyledLeftSide isMapExpanded={isMapExpanded}>
              <RestaurantCardList
                restaurantDataList={restaurantListData}
                loading={isLoading}
                setHoveredId={setHoveredId}
                setCurrentPage={setCurrentPage}
              />
            </StyledLeftSide>
            <StyledRightSide>
              <Map
                setCurrentPage={setCurrentPage}
                data={restaurantListData?.content}
                toggleMapExpand={toggleMapExpand}
                hoveredId={hoveredId}
                loadingData={isLoading}
              />
            </StyledRightSide>
          </StyledLayout>
          <Footer />
        </>
      )}
    </>
  );
}

export default MainPage;

const StyledNavbarSection = styled.section`
  display: grid;

  position: sticky;
  top: 80px;
  z-index: 10;

  & > *:first-child {
    border-right: 1px solid var(--gray-2);
  }
`;

const StyledLayer = styled.div<{ isMobile: boolean }>`
  display: flex;
  flex-direction: column;

  position: fixed;
  top: ${({ isMobile }) => (isMobile ? '140px' : '160px')};
  z-index: 0;

  width: 100%;
  height: 100%;
`;

const StyledMapBottomCover = styled.div<{ isBottomSheetOpen: boolean }>`
  position: fixed;
  bottom: 0;

  width: 100%;
  height: 0;

  background: var(--white);

  transition: height 0.8s ease-in-out;

  overflow: hidden;

  ${({ isBottomSheetOpen }) =>
    isBottomSheetOpen &&
    css`
      z-index: 20;

      height: calc(36vh - 74px);
    `}
`;

const StyledMobileLayout = styled.div`
  display: flex;
  align-items: flex-end;

  position: relative;

  width: 100%;
  height: 100vh;
`;

const StyledLayout = styled.div<{ isMapExpanded: boolean }>`
  display: grid;

  width: 100%;
  height: 100%;
  grid-template-columns: 63vw 37vw;

  ${({ isMapExpanded }) =>
    isMapExpanded &&
    css`
      grid-template-columns: 100vw;
    `}

  @media screen and (width <= 1240px) {
    grid-template-columns: 55vw 45vw;

    ${({ isMapExpanded }) =>
      isMapExpanded &&
      css`
        grid-template-columns: 100vw;
      `}
  }
`;

const StyledLeftSide = styled.div<{ isMapExpanded: boolean }>`
  z-index: 0;

  ${({ isMapExpanded }) =>
    isMapExpanded &&
    css`
      display: none;
    `}
`;

const StyledRightSide = styled.div`
  position: sticky;
  top: 240px;

  width: 100%;
  height: calc(100vh - 160px);
`;
