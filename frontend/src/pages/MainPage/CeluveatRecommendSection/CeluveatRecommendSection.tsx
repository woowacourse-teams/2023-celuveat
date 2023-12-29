import React, { Suspense } from 'react';
import Slider from 'react-slick';
import styled from 'styled-components';
import MiniRestaurantCardSkeleton from '~/components/MiniRestaurantCard/MiniRestaurantCardSkeleton';
import useMediaQuery from '~/hooks/useMediaQuery';
import { RestaurantCardCarouselSettings } from '~/constants/carouselSettings';
import CeluveatRecommendedRestaurantSlider from './components/CeluveatRecommendedRestaurantSlider';

function CeluveatRecommendSection() {
  const { isMobile } = useMediaQuery();

  return (
    <section>
      <StyledTitle>셀럽잇 추천 맛집!</StyledTitle>

      {isMobile && (
        <Suspense
          fallback={
            <>
              {new Array(5).fill(1).map(() => (
                <MiniRestaurantCardSkeleton flexColumn />
              ))}
            </>
          }
        >
          <CeluveatRecommendedRestaurantSlider />
        </Suspense>
      )}

      {!isMobile && (
        <StyledSliderContainer>
          <Suspense
            fallback={
              <Slider {...RestaurantCardCarouselSettings}>
                {new Array(5).fill(1).map(() => (
                  <MiniRestaurantCardSkeleton flexColumn />
                ))}
              </Slider>
            }
          >
            <CeluveatRecommendedRestaurantSlider />
          </Suspense>
        </StyledSliderContainer>
      )}
    </section>
  );
}

export default CeluveatRecommendSection;

const StyledTitle = styled.h5`
  margin-left: 1.6rem;
`;

const StyledSliderContainer = styled.div`
  padding: 2rem 4rem;
`;
