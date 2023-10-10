import { useQuery } from '@tanstack/react-query';
import styled, { css } from 'styled-components';
import { getNearByRestaurant } from '~/api/restaurant';
import RestaurantCard from '~/components/RestaurantCard';
import { hideScrollBar } from '~/styles/common';
import type { RestaurantListData } from '~/@types/api.types';
import useMediaQuery from '~/hooks/useMediaQuery';
import MiniRestaurantCard from '~/components/MiniRestaurantCard';

interface NearByRestaurantCardListProps {
  restaurantId: string;
}

function NearByRestaurantCardList({ restaurantId }: NearByRestaurantCardListProps) {
  const { data: nearByRestaurant } = useQuery<RestaurantListData>({
    queryKey: ['restaurants', 'nearby', restaurantId],
    queryFn: async () => getNearByRestaurant(restaurantId),
    suspense: true,
  });
  const { isMobile } = useMediaQuery();

  return (
    <StyledNearByRestaurant isMobile={isMobile}>
      <h5>주변 다른 식당</h5>
      <ul>
        {nearByRestaurant.content.map(restaurant =>
          isMobile ? (
            <MiniRestaurantCard restaurant={restaurant} celebs={restaurant.celebs} carousel={false} flexColumn />
          ) : (
            <StyledRestaurantCardContainer>
              <RestaurantCard type="map" restaurant={restaurant} celebs={restaurant.celebs} size="36px" />
            </StyledRestaurantCardContainer>
          ),
        )}
      </ul>
    </StyledNearByRestaurant>
  );
}

export default NearByRestaurantCardList;

const StyledNearByRestaurant = styled.section<{ isMobile: boolean }>`
  display: flex;
  flex-direction: column;

  margin: 3.2rem 0;

  & > ul {
    ${hideScrollBar}
    display: flex;
    gap: 0 2rem;
    overflow-x: scroll;

    padding: 2rem 0;

    ${({ isMobile }) =>
      !isMobile &&
      css`
        & > * {
          min-width: 300px;

          box-shadow: var(--shadow);
        }
      `}
  }
`;

const StyledRestaurantCardContainer = styled.div`
  border-radius: 12px;

  box-shadow: var(--map-shadow);
`;
