import { styled } from 'styled-components';
import React, { useEffect, useState } from 'react';
import RestaurantCard from '../RestaurantCard';
import { FONT_SIZE } from '~/styles/common';
import RestaurantCardListSkeleton from './RestaurantCardListSkeleton';

import type { RestaurantData, RestaurantListData } from '~/@types/api.types';
import PageNationBar from '../@common/PageNationBar';
import useMediaQuery from '~/hooks/useMediaQuery';

interface RestaurantCardListProps {
  restaurantDataList: RestaurantListData | null;
  loading: boolean;
  setHoveredId: React.Dispatch<React.SetStateAction<number>>;
  setCurrentPage: React.Dispatch<React.SetStateAction<number>>;
}

function RestaurantCardList({ restaurantDataList, loading, setHoveredId, setCurrentPage }: RestaurantCardListProps) {
  const { isMobile } = useMediaQuery();
  const [prevCardNumber, setPrevCardNumber] = useState(18);

  const clickPageButton: React.MouseEventHandler<HTMLButtonElement> = e => {
    const pageValue = e.currentTarget.value;
    window.scrollTo(0, 0);

    if (pageValue === 'prev') return setCurrentPage(prev => prev - 1);
    if (pageValue === 'next') return setCurrentPage(prev => prev + 1);
    return setCurrentPage(Number(pageValue) - 1);
  };

  useEffect(() => {
    if (restaurantDataList) setPrevCardNumber(restaurantDataList.currentElementsCount);
  }, [restaurantDataList?.currentElementsCount]);

  if (!restaurantDataList || loading) return <RestaurantCardListSkeleton cardNumber={prevCardNumber} />;

  return (
    <StyledRestaurantCardListContainer>
      {!isMobile && <StyledCardListHeader>음식점 수 {restaurantDataList.totalElementsCount} 개</StyledCardListHeader>}
      <StyledRestaurantCardList>
        {restaurantDataList.content?.map(({ celebs, ...restaurant }: RestaurantData) => (
          <RestaurantCard restaurant={restaurant} celebs={celebs} size="42px" setHoveredId={setHoveredId} />
        ))}
      </StyledRestaurantCardList>
      <PageNationBar
        totalPage={restaurantDataList.totalPage}
        currentPage={restaurantDataList.currentPage + 1}
        clickPageButton={clickPageButton}
      />
    </StyledRestaurantCardListContainer>
  );
}

export default React.memo(RestaurantCardList);

const StyledRestaurantCardListContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 3.2rem;

  margin: 3.2rem 2.4rem;
`;

const StyledCardListHeader = styled.p`
  font-size: ${FONT_SIZE.md};
  font-weight: 700;
`;

const StyledRestaurantCardList = styled.div`
  display: grid;
  gap: 4rem 2.4rem;

  height: 100%;

  grid-template-columns: 1fr 1fr 1fr;

  @media screen and (width <= 1240px) {
    grid-template-columns: 1fr 1fr;
  }

  @media screen and (width <= 743px) {
    grid-template-columns: 1fr;
  }
`;
