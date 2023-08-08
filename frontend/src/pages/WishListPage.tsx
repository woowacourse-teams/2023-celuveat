import { useQuery } from '@tanstack/react-query';
import { useState } from 'react';
import styled, { css } from 'styled-components';
import { RestaurantData } from '~/@types/api.types';
import { CoordinateBoundary } from '~/@types/map.types';
import userInstance from '~/api/RestaurantLike';
import Footer from '~/components/@common/Footer';

import Header from '~/components/@common/Header';
import Map from '~/components/@common/Map';
import RestaurantWishList from '~/components/RestaurantWishList';

function WishListPage() {
  const [isMapExpanded, setIsMapExpanded] = useState(false);
  const [, setBoundary] = useState<CoordinateBoundary>();
  const [hoveredId, setHoveredId] = useState<number | null>(null);

  const { data: restaurantData, isLoading } = useQuery<RestaurantData[]>({
    queryKey: ['restaurants', 'like'],
    queryFn: () => userInstance.get('restaurants/like').then(response => response.data),
  });

  const toggleMapExpand = () => setIsMapExpanded(prev => !prev);

  return (
    <div>
      <Header />
      <StyledWishListWrapper>
        <StyledLayout isMapExpanded={isMapExpanded}>
          <StyledLeftSide isMapExpanded={isMapExpanded}>
            <h2>위시리스트</h2>
            <RestaurantWishList restaurantData={restaurantData} setHoveredId={setHoveredId} />
          </StyledLeftSide>
          <StyledRightSide>
            <Map
              setBoundary={setBoundary}
              setCurrentPage={() => {}}
              data={restaurantData}
              toggleMapExpand={toggleMapExpand}
              hoveredId={hoveredId}
              loadingData={isLoading}
            />
          </StyledRightSide>
        </StyledLayout>
      </StyledWishListWrapper>
      <Footer />
    </div>
  );
}

export default WishListPage;

const StyledWishListWrapper = styled.section`
  padding: 1.2rem 2.4rem;
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
  top: 160px;

  width: 100%;
  height: calc(100vh - 160px);
`;
