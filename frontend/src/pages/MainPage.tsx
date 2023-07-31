/* eslint-disable import/no-extraneous-dependencies */
import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { styled, css } from 'styled-components';
import Footer from '~/components/@common/Footer';
import Header from '~/components/@common/Header';
import Map from '~/components/@common/Map';
import CategoryNavbar from '~/components/CategoryNavbar';
import CelebDropDown from '~/components/CelebDropDown/CelebDropDown';
import RESTAURANT_CATEGORY from '~/constants/restaurantCategory';
import { CELEBS_OPTIONS } from '~/constants/celebs';
import RestaurantCardList from '~/components/RestaurantCardList';
import { getRestaurants } from '~/api';

import type { Celeb } from '~/@types/celeb.types';
import type { CoordinateBoundary } from '~/@types/map.types';
import type { RestaurantCategory } from '~/@types/restaurant.types';
import type { RestaurantListData } from '~/@types/api.types';

function MainPage() {
  const [isMapExpanded, setIsMapExpanded] = useState(false);
  const [boundary, setBoundary] = useState<CoordinateBoundary>();
  const [celebId, setCelebId] = useState<Celeb['id']>(-1);
  const [currentPage, setCurrentPage] = useState(0);
  const [restaurantCategory, setRestaurantCategory] = useState<RestaurantCategory>('전체');
  const [hoveredId, setHoveredId] = useState<number | null>(null);

  const { data, isLoading, refetch } = useQuery<RestaurantListData>({
    queryKey: ['restaurants', boundary, celebId, restaurantCategory, currentPage],
    queryFn: () => getRestaurants({ boundary, celebId, category: restaurantCategory, page: currentPage }),
  });

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

  return (
    <>
      <Header />
      <StyledNavBar>
        <CelebDropDown celebs={CELEBS_OPTIONS} externalOnClick={clickCeleb} />
        <StyledLine />
        <CategoryNavbar categories={RESTAURANT_CATEGORY} externalOnClick={clickRestaurantCategory} />
      </StyledNavBar>
      <StyledLayout isMapExpanded={isMapExpanded}>
        <StyledLeftSide isMapExpanded={isMapExpanded}>
          <RestaurantCardList
            restaurantDataList={data}
            loading={isLoading}
            setHoveredId={setHoveredId}
            setCurrentPage={setCurrentPage}
          />
        </StyledLeftSide>
        <StyledRightSide>
          <Map
            setBoundary={setBoundary}
            setCurrentPage={setCurrentPage}
            data={data?.content}
            toggleMapExpand={toggleMapExpand}
            hoveredId={hoveredId}
            loadingData={isLoading}
          />
        </StyledRightSide>
      </StyledLayout>
      <Footer />
    </>
  );
}

export default MainPage;

const StyledNavBar = styled.div`
  display: flex;
  align-items: center;

  position: sticky;
  top: 80px;
  z-index: 10;

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
