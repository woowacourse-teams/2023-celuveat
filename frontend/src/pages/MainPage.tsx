import { useEffect, useState } from 'react';
import { styled, css } from 'styled-components';
import { useMutation, useQuery } from '@tanstack/react-query';
import Footer from '~/components/@common/Footer';
import Header from '~/components/@common/Header';
import Map from '~/components/@common/Map';
import CategoryNavbar from '~/components/CategoryNavbar';
import CelebDropDown from '~/components/CelebDropDown/CelebDropDown';
import RESTAURANT_CATEGORY from '~/constants/restaurantCategory';
import { OPTION_FOR_CELEB_ALL } from '~/constants/options';
import useMediaQuery from '~/hooks/useMediaQuery';
import BottomSheet from '~/components/@common/BottomSheet';
import RestaurantCardList from '~/components/RestaurantCardList';
import { getCelebs, getRestaurants } from '~/api';

import type { Celeb } from '~/@types/celeb.types';
import type { CoordinateBoundary } from '~/@types/map.types';
import type { RestaurantCategory } from '~/@types/restaurant.types';
import type { RestaurantListData } from '~/@types/api.types';
import useBottomSheetStatus from '~/hooks/store/useBottomSheetStatus';

function MainPage() {
  const isBottomSheetOpen = useBottomSheetStatus(state => state.isOpen);
  const { isMobile } = useMediaQuery();
  const [isMapExpanded, setIsMapExpanded] = useState(false);
  const [boundary, setBoundary] = useState<CoordinateBoundary>();
  const [celebId, setCelebId] = useState<Celeb['id']>(-1);
  const [currentPage, setCurrentPage] = useState(0);
  const [restaurantCategory, setRestaurantCategory] = useState<RestaurantCategory>('전체');
  const [hoveredId, setHoveredId] = useState<number | null>(null);
  const [celebOptions, setCelebOptions] = useState<Celeb[]>();

  const {
    data: restaurantListData,
    isLoading,
    refetch,
  } = useQuery<RestaurantListData>({
    queryKey: ['restaurants', boundary, celebId, restaurantCategory, currentPage],
    queryFn: () => getRestaurants({ boundary, celebId, category: restaurantCategory, page: currentPage }),
  });

  const celebOptionsMutation = useMutation({
    mutationFn: () => getCelebs(),
    onSuccess: (data: Celeb[]) => {
      setCelebOptions([OPTION_FOR_CELEB_ALL, ...data]);
    },
  });

  useEffect(() => {
    celebOptionsMutation.mutate();
  }, []);

  const clickRestaurantCategory = (e: React.MouseEvent<HTMLElement>) => {
    const currentCategory = e.currentTarget.dataset.label as RestaurantCategory;

    setRestaurantCategory(currentCategory);
    setCurrentPage(0);
    refetch();
  };

  const clickCeleb = (e: React.MouseEvent<HTMLElement>) => {
    const currentCelebId = Number(e.currentTarget.dataset.id);

    setCelebId(currentCelebId);
    setCurrentPage(0);
    refetch();
  };

  const toggleMapExpand = () => setIsMapExpanded(prev => !prev);
  const bottomSheetHeader = (total: number) => `지도 영역에 있는 음식점 수 ${total}개`;

  return (
    <>
      <Header />
      <StyledNavBar isMobile={isMobile}>
        <CelebDropDown celebs={celebOptions} externalOnClick={clickCeleb} />
        <StyledLine />
        <CategoryNavbar categories={RESTAURANT_CATEGORY} externalOnClick={clickRestaurantCategory} />
      </StyledNavBar>
      {isMobile ? (
        <StyledMobileLayout>
          <StyledLayer>
            <Map
              setBoundary={setBoundary}
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
                setBoundary={setBoundary}
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

const StyledNavBar = styled.div<{ isMobile: boolean }>`
  display: flex;
  align-items: center;

  position: ${({ isMobile }) => (isMobile ? 'fixed' : 'sticky')};
  top: 80px;
  z-index: 11;

  width: 100%;
  height: 80px;

  background-color: var(--white);
  border-bottom: 1px solid var(--gray-1);
`;

const StyledLine = styled.div`
  width: 1px;
  height: 46px;

  background-color: var(--gray-3);
`;

const StyledLayer = styled.div`
  display: flex;
  flex-direction: column;

  position: fixed;
  top: 160px;
  z-index: 0;

  width: 100%;
  height: 100%;
`;

const StyledMapBottomCover = styled.div<{ isBottomSheetOpen: boolean }>`
  width: 100%;
  height: 0;
  overflow: hidden;

  transition: height 0.8s ease-in-out;

  ${({ isBottomSheetOpen }) =>
    isBottomSheetOpen &&
    css`
      height: 800px;
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
  grid-template-columns: 63% 37%;

  ${({ isMapExpanded }) =>
    isMapExpanded &&
    css`
      grid-template-columns: 100%;
    `}

  @media screen and (width <= 1240px) {
    grid-template-columns: 55% 45%;

    ${({ isMapExpanded }) =>
      isMapExpanded &&
      css`
        grid-template-columns: 100%;
      `}
  }

  @media screen and (width <= 950px) {
    & > div:last-child {
      display: none;
    }

    grid-template-columns: 100% 0;
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
  top: 160px;

  width: 100%;
  height: calc(100vh - 160px);
`;
