/* eslint-disable no-nested-ternary */
import { useQuery } from '@tanstack/react-query';
import styled from 'styled-components';
import Slider from 'react-slick';
import { getNearByRestaurant } from '~/api/restaurant';
import { hideScrollBar } from '~/styles/common';
import type { RestaurantListData } from '~/@types/api.types';
import useMediaQuery from '~/hooks/useMediaQuery';
import MiniRestaurantCard from '~/components/MiniRestaurantCard';
import { RestaurantCardCarouselSettings } from '~/constants/carouselSettings';

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
      {nearByRestaurant.content.length === 0 ? (
        <div>
          <h5>주변에 다른 식당이 없어요.</h5>
        </div>
      ) : isMobile || nearByRestaurant.content.length <= 5 ? (
        <StyledCardContainer>
          {nearByRestaurant.content.map(restaurant => (
            <MiniRestaurantCard
              restaurant={restaurant}
              celebs={restaurant.celebs}
              flexColumn
              showLike
              showRating
              showDistance
            />
          ))}
        </StyledCardContainer>
      ) : (
        <StyledSliderContainer>
          <Slider {...RestaurantCardCarouselSettings}>
            {nearByRestaurant.content.map(restaurant => (
              <MiniRestaurantCard
                restaurant={restaurant}
                celebs={restaurant.celebs}
                flexColumn
                showLike
                showRating
                showDistance
                isCarouselItem
              />
            ))}
          </Slider>
        </StyledSliderContainer>
      )}
    </StyledNearByRestaurant>
  );
}

export default NearByRestaurantCardList;

const StyledNearByRestaurant = styled.section<{ isMobile: boolean }>`
  display: flex;
  flex-direction: column;

  margin: 3.2rem 0;

  & > div > h5 {
    margin: 4rem 0;

    color: var(--gray-3);
    text-align: center;
  }
`;

const StyledCardContainer = styled.div`
  ${hideScrollBar}
  display: flex;
  gap: 0 2rem;
  overflow-x: scroll;

  width: 100%;

  padding: 2rem 0;
`;

const StyledSliderContainer = styled.div`
  padding: 2rem 4rem;
`;
