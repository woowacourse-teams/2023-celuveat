import { useInfiniteQuery } from '@tanstack/react-query';
import { Link, useParams } from 'react-router-dom';
import styled from 'styled-components';
import { RestaurantListData } from '~/@types/api.types';
import { Region } from '~/@types/region.types';
import { getRestaurantsByAddress } from '~/api/restaurant';
import SearchResultBox from '~/components/SearchResultBox';
import { RECOMMENDED_REGION } from '~/constants/recommendedRegion';
import { SERVER_IMG_URL } from '~/constants/url';
import useInfiniteScroll from '~/hooks/useInfiniteScroll';
import { FONT_SIZE } from '~/styles/common';

function RegionResultPage() {
  const { region } = useParams<{ region: Region }>();

  const {
    data: restaurantDataPages,
    fetchNextPage,
    isFetchingNextPage,
  } = useInfiniteQuery<RestaurantListData>({
    queryKey: ['restaurantsFilteredByRegion', region],
    queryFn: ({ pageParam = 0 }) =>
      getRestaurantsByAddress({ codes: RECOMMENDED_REGION[region].code, page: pageParam }),
    getNextPageParam: lastPage => {
      if (lastPage.totalPage > lastPage.currentPage) return lastPage.currentPage + 1;
      return undefined;
    },
  });

  useInfiniteScroll({ isFetchingNextPage, fetchNextPage, restaurantDataPages });

  return (
    <StyledContainer>
      <StyledLink to="/">
        <h5> ← {RECOMMENDED_REGION[region].name}</h5>
      </StyledLink>
      <StyledBanner src={`${SERVER_IMG_URL}regions/${region}.jpeg`} alt={region} />
      <StyledResultCount>
        {restaurantDataPages && restaurantDataPages.pages[0].totalElementsCount}개의 매장
      </StyledResultCount>
      {restaurantDataPages?.pages.map(restaurantDataList => (
        <SearchResultBox restaurantDataList={restaurantDataList} />
      ))}{' '}
    </StyledContainer>
  );
}

export default RegionResultPage;

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;

  width: 100%;
  min-height: 100vh;

  padding: 1.6rem 1.2rem;
`;

const StyledLink = styled(Link)`
  text-decoration: none;
`;

const StyledResultCount = styled.span`
  font-size: ${FONT_SIZE.md};
`;

const StyledBanner = styled.img`
  width: 100%;
  max-height: 160px;

  overflow: hidden;
  object-fit: cover;

  border-radius: 12px;
`;
