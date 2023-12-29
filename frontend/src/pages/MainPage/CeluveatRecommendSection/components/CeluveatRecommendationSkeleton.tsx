import React from 'react';
import Slider from 'react-slick';
import MiniRestaurantCardSkeleton from '~/components/MiniRestaurantCard/MiniRestaurantCardSkeleton';
import { RestaurantCardCarouselSettings } from '~/constants/carouselSettings';
import useMediaQuery from '~/hooks/useMediaQuery';

function CeluveatRecommendationSkeleton() {
  const { isMobile } = useMediaQuery();

  if (isMobile) return new Array(5).fill(1).map(() => <MiniRestaurantCardSkeleton flexColumn />);

  return (
    <Slider {...RestaurantCardCarouselSettings}>
      {new Array(5).fill(1).map(() => (
        <MiniRestaurantCardSkeleton flexColumn isCarouselItem />
      ))}
    </Slider>
  );
}

export default CeluveatRecommendationSkeleton;
