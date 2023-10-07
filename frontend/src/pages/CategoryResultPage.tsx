import { useInfiniteQuery } from '@tanstack/react-query';
import { Link, useParams } from 'react-router-dom';
import styled from 'styled-components';
import { RestaurantListData } from '~/@types/api.types';
import { getRestaurants } from '~/api/restaurant';
import SearchResultBox from '~/components/SearchResultBox';
import { WHOLE_BOUNDARY } from '~/constants/boundary';
import useInfiniteScroll from '~/hooks/useInfiniteScroll';
import { FONT_SIZE } from '~/styles/common';

function CategoryResultPage() {
  const { category } = useParams();

  const {
    data: restaurantDataPages,
    fetchNextPage,
    isFetchingNextPage,
  } = useInfiniteQuery<RestaurantListData>({
    queryKey: ['restaurants', category],
    queryFn: ({ pageParam = 0 }) =>
      getRestaurants({
        boundary: WHOLE_BOUNDARY,
        category,
        sort: 'like',
        page: pageParam,
      }),
    getNextPageParam: lastPage => {
      if (lastPage.totalPage > lastPage.currentPage) return lastPage.currentPage + 1;
      return undefined;
    },
  });

  useInfiniteScroll({ isFetchingNextPage, fetchNextPage, restaurantDataPages });

  return (
    <StyledContainer>
      <StyledLink to="/">
        <h5> ← {category} </h5>
      </StyledLink>
      <StyledResultCount>
        {restaurantDataPages && restaurantDataPages.pages[0].totalElementsCount}개의 매장
      </StyledResultCount>

      {restaurantDataPages?.pages.map(restaurantDataList => (
        <SearchResultBox restaurantDataList={restaurantDataList} />
      ))}
    </StyledContainer>
  );
}

export default CategoryResultPage;

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;

  width: 100%;
  min-height: 100vh;
  overflow-x: hidden;

  padding: 1.6rem 1.2rem;
`;

const StyledLink = styled(Link)`
  text-decoration: none;
`;

const StyledResultCount = styled.span`
  font-size: ${FONT_SIZE.md};
`;
