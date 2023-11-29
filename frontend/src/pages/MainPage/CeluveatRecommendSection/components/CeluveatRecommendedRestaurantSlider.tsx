import { useQuery } from '@tanstack/react-query';
import React from 'react';
import Slider from 'react-slick';
import { getRecommendedRestaurants } from '~/api/restaurant';
import MiniRestaurantCard from '~/components/MiniRestaurantCard';
import { RestaurantCardCarouselSettings } from '~/constants/carouselSettings';

import type { RestaurantData } from '~/@types/api.types';

function CeluveatRecommendedRestaurantSlider() {
  const { data: recommendedRestaurantData } = useQuery<RestaurantData[]>({
    queryKey: ['recommendedRestaurants'],
    queryFn: getRecommendedRestaurants,
    suspense: true,
  });

  return (
    <Slider {...RestaurantCardCarouselSettings}>
      {recommendedRestaurantData?.map(({ celebs, ...restaurant }) => (
        <MiniRestaurantCard celebs={celebs} restaurant={restaurant} flexColumn showRating isCarouselItem showLike />
      ))}
    </Slider>
  );
}

export default CeluveatRecommendedRestaurantSlider;
