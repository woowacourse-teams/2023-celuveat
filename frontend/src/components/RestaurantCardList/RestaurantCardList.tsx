import { styled } from 'styled-components';
import { useEffect, useState } from 'react';
import RestaurantCard from '../RestaurantCard';
import { FONT_SIZE } from '~/styles/common';
import RestaurantCardListSkeleton from './RestaurantCardListSkeleton';

import type { RestaurantData, RestaurantListData } from '~/@types/api.types';
import PageNationBar from '../@common/PageNationBar';

interface RestaurantCardListProps {
  restaurantDataList: RestaurantListData | null;
  loading: boolean;
  setHoveredId: React.Dispatch<React.SetStateAction<number>>;
  setCurrentPage: React.Dispatch<React.SetStateAction<number>>;
}

function RestaurantCardList({ restaurantDataList, loading, setHoveredId, setCurrentPage }: RestaurantCardListProps) {
  const [prevCardNumber, setPrevCardNumber] = useState(18);

  const clickPageButton: React.MouseEventHandler<HTMLButtonElement> = e => {
    const currentPage = Number(e.currentTarget.value) - 1;
    setCurrentPage(currentPage);
    window.scrollTo(0, 0);
  };

  useEffect(() => {
    if (restaurantDataList) setPrevCardNumber(restaurantDataList.currentElementsCount);
  }, [restaurantDataList?.currentElementsCount]);

  if (!restaurantDataList || loading) return <RestaurantCardListSkeleton cardNumber={prevCardNumber} />;

  return (
    <StyledRestaurantCardListContainer>
      <StyledCardListHeader>음식점 수 {restaurantDataList.totalElementsCount} 개</StyledCardListHeader>
      <StyledRestaurantCardList>
        {restaurantDataList.content?.map(({ celebs, ...restaurant }: RestaurantData) => (
          <RestaurantCard restaurant={restaurant} celebs={celebs} size="42px" setHoveredId={setHoveredId} />
        ))}
      </StyledRestaurantCardList>
      <PageNationBar
        totalPage={restaurantDataList.totalPage}
        currentPage={restaurantDataList.currentPage}
        clickPageButton={clickPageButton}
      />
    </StyledRestaurantCardListContainer>
  );
}

export default RestaurantCardList;

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
`;
