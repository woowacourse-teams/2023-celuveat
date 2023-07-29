import { styled } from 'styled-components';
import RestaurantCard from '../RestaurantCard/RestaurantCard';
import type { RestaurantData, RestaurantListData } from '~/@types/api.types';
import { FONT_SIZE } from '~/styles/common';
import RestaurantCardListSkeleton from './RestaurantCardListSkeleton';

interface RestaurantCardListProps {
  restaurantDataList: RestaurantListData | null;
  loading: boolean;
}

function RestaurantCardList({ restaurantDataList, loading }: RestaurantCardListProps) {
  if (!restaurantDataList || loading) return <RestaurantCardListSkeleton />;

  const { content, totalElementsCount } = restaurantDataList;

  return (
    <div>
      <StyledCardListHeader>음식점 수 {totalElementsCount} 개</StyledCardListHeader>
      <StyledRestaurantCardList>
        {content?.map(({ celebs, ...restaurant }: RestaurantData) => (
          <RestaurantCard restaurant={restaurant} celebs={celebs} size={42} onClick={() => {}} />
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
