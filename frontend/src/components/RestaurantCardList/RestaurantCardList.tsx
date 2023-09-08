import { styled, css } from 'styled-components';
import React, { useEffect, useState } from 'react';
import { shallow } from 'zustand/shallow';
import { useQuery } from '@tanstack/react-query';
import RestaurantCard from '../RestaurantCard';
import { FONT_SIZE } from '~/styles/common';
import RestaurantCardListSkeleton from './RestaurantCardListSkeleton';
import PageNationBar from '../@common/PageNationBar';
import useMediaQuery from '~/hooks/useMediaQuery';
import useRestaurantsQueryStringState from '~/hooks/store/useRestaurantsQueryStringState';
import { getMSWRestaurants } from '~/api';

import type { RestaurantData, RestaurantListData } from '~/@types/api.types';
import useHoveredRestaurantState from '~/hooks/store/useHoveredRestaurantState';

function RestaurantCardList() {
  const { isMobile } = useMediaQuery();
  const [prevCardNumber, setPrevCardNumber] = useState(18);
  const [boundary, celebId, currentPage, restaurantCategory, setCurrentPage] = useRestaurantsQueryStringState(
    state => [state.boundary, state.celebId, state.currentPage, state.restaurantCategory, state.setCurrentPage],
    shallow,
  );

  const { data: restaurantDataList, isLoading } = useQuery<RestaurantListData>({
    queryKey: ['restaurants', boundary, celebId, restaurantCategory, currentPage],
    queryFn: () => getMSWRestaurants({ boundary, celebId, category: restaurantCategory, page: currentPage }),
  });

  const [setHoveredId] = useHoveredRestaurantState(state => [state.setId]);

  const clickPageButton: React.MouseEventHandler<HTMLButtonElement> = e => {
    e.stopPropagation();
    const pageValue = e.currentTarget.value;
    window.scrollTo(0, 0);

    if (pageValue === 'prev') return setCurrentPage(currentPage - 1);
    if (pageValue === 'next') return setCurrentPage(currentPage + 1);
    return setCurrentPage(Number(pageValue) - 1);
  };

  useEffect(() => {
    if (restaurantDataList) setPrevCardNumber(restaurantDataList.currentElementsCount);
  }, [restaurantDataList?.currentElementsCount]);

  if (isLoading)
    return (
      <StyledSkeleton>
        <RestaurantCardListSkeleton cardNumber={prevCardNumber} />
      </StyledSkeleton>
    );

  return (
    <StyledRestaurantCardListContainer data-cy="음식점 리스트" aria-label="음식점 리스트">
      {restaurantDataList.content.length !== 0 ? (
        <>
          {!isMobile && (
            <StyledCardListHeader>음식점 수 {restaurantDataList.totalElementsCount} 개</StyledCardListHeader>
          )}
          <StyledRestaurantCardList isMobile={isMobile}>
            {restaurantDataList.content?.map(({ celebs, ...restaurant }: RestaurantData) => (
              <RestaurantCard restaurant={restaurant} celebs={celebs} size="42px" setHoveredId={setHoveredId} />
            ))}
          </StyledRestaurantCardList>
          <PageNationBar
            totalPage={restaurantDataList.totalPage}
            currentPage={restaurantDataList.currentPage + 1}
            clickPageButton={clickPageButton}
          />
        </>
      ) : (
        <>
          <h3>일치하는 음식점이 없어요.</h3>
          <StyledDescription>
            더 많은 음식점을 둘러보려면 필터링 옵션 살펴보거나 지도를 움직여 주세요!
          </StyledDescription>
        </>
      )}
    </StyledRestaurantCardListContainer>
  );
}

export default React.memo(RestaurantCardList);

const StyledSkeleton = styled.div`
  padding-bottom: 3.2rem;
`;

const StyledRestaurantCardListContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 3.2rem;

  margin: 3.2rem 2.4rem;
`;

const StyledDescription = styled.div`
  font-size: ${FONT_SIZE.md};
`;

const StyledCardListHeader = styled.p`
  font-size: ${FONT_SIZE.md};
  font-weight: 700;
`;

const StyledRestaurantCardList = styled.div<{ isMobile: boolean }>`
  display: grid;
  gap: 4rem 2.4rem;

  height: 100%;

  grid-template-columns: 1fr 1fr 1fr;

  @media screen and (width <= 1240px) {
    grid-template-columns: 1fr 1fr;
  }

  ${({ isMobile }) =>
    isMobile
      ? css`
          grid-template-columns: 1fr 1fr;

          @media screen and (width <= 550px) {
            grid-template-columns: 1fr;
          }
        `
      : css`
          @media screen and (width <= 743px) {
            grid-template-columns: 1fr;
          }
        `}
`;
