import { useQuery } from '@tanstack/react-query';
import React from 'react';
import styled from 'styled-components';
import { getRecommendedRestaurants } from '~/api/restaurant';
import MiniRestaurantCard from '~/components/MiniRestaurantCard';
import { hideScrollBar } from '~/styles/common';

import type { RestaurantData } from '~/@types/api.types';

function CeluveatRecommendedRestaurants() {
  const { data: recommendedRestaurantData } = useQuery<RestaurantData[]>({
    queryKey: ['recommendedRestaurants'],
    queryFn: getRecommendedRestaurants,
    suspense: true,
  });

  return (
    <StyledPopularRestaurantBox>
      {recommendedRestaurantData.map(({ celebs, ...restaurant }) => (
        <MiniRestaurantCard celebs={celebs} restaurant={restaurant} flexColumn showRating />
      ))}
    </StyledPopularRestaurantBox>
  );
}

export default CeluveatRecommendedRestaurants;

const StyledPopularRestaurantBox = styled.div`
  display: flex;
  align-items: center;
  gap: 1.2rem;

  padding: 1.6rem;

  overflow-x: scroll;

  ${hideScrollBar}
`;
