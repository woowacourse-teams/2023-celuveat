/* eslint-disable react/no-unstable-nested-components */
import React, { Suspense } from 'react';
import styled from 'styled-components';
import { QueryErrorResetBoundary } from '@tanstack/react-query';
import useMediaQuery from '~/hooks/useMediaQuery';
import CeluveatRecommendedRestaurantSlider from './components/CeluveatRecommendedRestaurantSlider';
import CeluveatRecommendationSkeleton from './components/CeluveatRecommendationSkeleton';
import CeluveatRecommendedRestaurants from './components/CeluveatRecommendedRestaurants';
import ErrorBoundary from '~/components/@common/ErrorBoundary/ErrorBoundary';
import TextButton from '~/components/@common/Button';

function CeluveatRecommendSection() {
  const { isMobile } = useMediaQuery();

  return (
    <section>
      <StyledTitle>셀럽잇 추천 맛집!</StyledTitle>
      <QueryErrorResetBoundary>
        {({ reset }) => (
          <ErrorBoundary
            fallbackRender={({ resetErrorBoundary }) => (
              <div>
                알수없는 에러가 발생했습니다
                <TextButton text="재요청" onClick={() => resetErrorBoundary()} type="button" colorType="light" />
              </div>
            )}
            reset={reset}
          >
            {isMobile && (
              <Suspense fallback={<CeluveatRecommendationSkeleton />}>
                <CeluveatRecommendedRestaurants />
              </Suspense>
            )}

            {!isMobile && (
              <StyledSliderContainer>
                <Suspense fallback={<CeluveatRecommendationSkeleton />}>
                  <CeluveatRecommendedRestaurantSlider />
                </Suspense>
              </StyledSliderContainer>
            )}
          </ErrorBoundary>
        )}
      </QueryErrorResetBoundary>
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
