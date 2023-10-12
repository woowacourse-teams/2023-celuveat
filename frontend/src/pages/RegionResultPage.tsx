import { useInfiniteQuery } from '@tanstack/react-query';
import { useEffect, useRef } from 'react';
import { Link, useParams } from 'react-router-dom';
import styled from 'styled-components';
import { RestaurantListData } from '~/@types/api.types';
import { Region } from '~/@types/region.types';
import { getRestaurantsByAddress } from '~/api/restaurant';
import SearchResultBox from '~/components/SearchResultBox';
import { RECOMMENDED_REGION } from '~/constants/recommendedRegion';
import { SERVER_IMG_URL } from '~/constants/url';
import { useIntersectionObserver } from '~/hooks/useIntersectionObserver';
import { FONT_SIZE } from '~/styles/common';

function RegionResultPage() {
  const { region } = useParams<{ region: Region }>();
  const ref = useRef<HTMLDivElement>();

  const { data: restaurantDataPages, fetchNextPage } = useInfiniteQuery<RestaurantListData>({
    queryKey: ['restaurantsFilteredByRegion', region],
    queryFn: ({ pageParam = 0 }) =>
      getRestaurantsByAddress({ codes: RECOMMENDED_REGION[region].code, page: pageParam }),
    getNextPageParam: lastPage => {
      if (lastPage.totalPage > lastPage.currentPage) return lastPage.currentPage + 1;
      return undefined;
    },
    suspense: true,
  });

  const entry = useIntersectionObserver(ref, {});

  useEffect(() => {
    if (entry) fetchNextPage();
  }, [entry]);

  return (
    <StyledContainer>
      <StyledBanner imgUrl={`${SERVER_IMG_URL}regions/${region}.jpeg`}>
        <StyledLink to="/">
          <StyledTitle> ← {RECOMMENDED_REGION[region].name.join(',')} 맛집</StyledTitle>
        </StyledLink>
      </StyledBanner>
      <StyledResultSection>
        <StyledResultCount>
          {restaurantDataPages && restaurantDataPages.pages[0].totalElementsCount}개의 매장
        </StyledResultCount>

        {restaurantDataPages?.pages.map(restaurantDataList => (
          <SearchResultBox restaurantDataList={restaurantDataList} />
        ))}
      </StyledResultSection>
      <div ref={ref} />
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
`;

const StyledResultCount = styled.span`
  font-size: ${FONT_SIZE.md};
`;

const StyledBanner = styled.div<{ imgUrl: string }>`
  position: relative;

  width: 100%;
  height: 200px;

  background: ${({ imgUrl }) => `linear-gradient(rgb(0 0 0 / 40%),transparent,transparent), url(${imgUrl})`};
  background-size: cover;
`;

const StyledLink = styled(Link)`
  text-decoration: none;
`;

const StyledResultSection = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;

  width: 100%;

  padding: 0 1.2rem;
`;

const StyledTitle = styled.h5`
  margin: 1.6rem 1.2rem;

  color: var(--white);
`;
