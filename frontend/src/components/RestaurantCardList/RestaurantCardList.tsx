import { styled } from 'styled-components';
import { useEffect, useState } from 'react';
import RestaurantCard from '../RestaurantCard/RestaurantCard';
import { FONT_SIZE } from '~/styles/common';
import RestaurantCardListSkeleton from './RestaurantCardListSkeleton';

import type { RestaurantData, RestaurantListData } from '~/@types/api.types';

interface RestaurantCardListProps {
  restaurantDataList: RestaurantListData | null;
  loading: boolean;
  setHoveredId: React.Dispatch<React.SetStateAction<number>>;
}

function RestaurantCardList({ restaurantDataList, loading, setHoveredId }: RestaurantCardListProps) {
  const [prevCardNumber, setPrevCardNumber] = useState(18);

  useEffect(() => {
    if (restaurantDataList) setPrevCardNumber(restaurantDataList.currentElementsCount);
  }, [restaurantDataList?.currentElementsCount]);

  if (!restaurantDataList || loading) return <RestaurantCardListSkeleton cardNumber={prevCardNumber} />;

  return (
    <div>
      <StyledCardListHeader>음식점 수 {restaurantDataList.totalElementsCount} 개</StyledCardListHeader>
      <StyledRestaurantCardList>
        {restaurantDataList.content?.map(({ celebs, ...restaurant }: RestaurantData) => (
          <RestaurantCard restaurant={restaurant} celebs={celebs} size="42px" setHoveredId={setHoveredId} />
        ))}
      </StyledRestaurantCardList>
    </div>
  );
}

export default RestaurantCardList;

const StyledCardListHeader = styled.p`
  margin: 3.2rem 2.4rem;

  font-size: ${FONT_SIZE.md};
`;

const StyledRestaurantCardList = styled.div`
  display: grid;
  gap: 4rem 2.4rem;

  height: 100%;

  margin: 0 2.4rem;
  grid-template-columns: 1fr 1fr 1fr;

  @media screen and (width <= 1240px) {
    grid-template-columns: 1fr 1fr;
  }
`;
