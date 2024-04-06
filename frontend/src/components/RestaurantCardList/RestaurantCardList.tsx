import React, { useEffect, useRef } from 'react';
import { styled } from 'styled-components';
import { shallow } from 'zustand/shallow';
import { useQuery } from '@tanstack/react-query';
import RestaurantCard from '../RestaurantCard';
import { FONT_SIZE } from '~/styles/common';
import RestaurantCardListSkeleton from './RestaurantCardListSkeleton';
import PageNationBar from '../@common/PageNationBar';
import useMediaQuery from '~/hooks/useMediaQuery';
import useRestaurantsQueryStringState from '~/hooks/store/useRestaurantsQueryStringState';

import type { RestaurantData, RestaurantListData } from '~/@types/api.types';
import useHoveredRestaurantState from '~/hooks/store/useHoveredRestaurantState';

import FilterSelectBox from '../FilterSelectBox';
import { getRestaurants } from '~/api/restaurant';

function RestaurantCardList({ scrollTop }: { scrollTop: () => void }) {
  const ref = useRef<HTMLDivElement>();
  const { isMobile } = useMediaQuery();
  const [boundary, celebId, currentPage, restaurantCategory, setCurrentPage, sort, setSort] =
    useRestaurantsQueryStringState(
      state => [
        state.boundary,
        state.celebId,
        state.currentPage,
        state.restaurantCategory,
        state.setCurrentPage,
        state.sort,
        state.setSort,
      ],
      shallow,
    );

  const { data: restaurantDataList } = useQuery<RestaurantListData>({
    queryKey: ['restaurants', boundary, celebId, restaurantCategory, currentPage, sort],
    queryFn: () => getRestaurants({ boundary, celebId, category: restaurantCategory, page: currentPage, sort }),
    keepPreviousData: true,
  });

  const [setHoveredId] = useHoveredRestaurantState(state => [state.setId]);

  const clickPageButton: React.MouseEventHandler<HTMLButtonElement> = e => {
    e.stopPropagation();
    const pageValue = e.currentTarget.value;
    ref.current.scrollTo(0, 0);
    scrollTop();

    if (pageValue === 'prev') return setCurrentPage(currentPage - 1);
    if (pageValue === 'next') return setCurrentPage(currentPage + 1);
    return setCurrentPage(Number(pageValue) - 1);
  };

  useEffect(() => {
    if (isMobile) setSort('distance');
  }, []);

  if (!restaurantDataList)
    return (
      <StyledSkeleton>
        <RestaurantCardListSkeleton cardNumber={restaurantDataList?.content?.length || 18} />
      </StyledSkeleton>
    );

  return (
    <StyledRestaurantCardListContainer data-cy="음식점 리스트" aria-label="음식점 리스트" ref={ref}>
      {restaurantDataList.content.length !== 0 ? (
        <>
          {!isMobile && (
            <StyledCardListHeader>
              <StyledRestaurantCount>음식점 수 {restaurantDataList.totalElementsCount} 개</StyledRestaurantCount>
              <FilterSelectBox />
            </StyledCardListHeader>
          )}
          <StyledRestaurantCardList>
            {restaurantDataList.content?.map(({ celebs, ...restaurant }: RestaurantData) => (
              <RestaurantCard
                key={`${restaurant.id}${celebs[0].id}`}
                restaurant={restaurant}
                celebs={celebs}
                size="42px"
                setHoveredId={setHoveredId}
              />
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

export default RestaurantCardList;

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

const StyledCardListHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;

  width: 100%;
`;

const StyledRestaurantCount = styled.span`
  font-size: ${FONT_SIZE.md};
  font-weight: 700;
`;

const StyledRestaurantCardList = styled.div`
  display: grid;
  gap: 4rem 2.4rem;

  height: 100%;

  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
`;
