import { useQuery } from '@tanstack/react-query';
import { Link, useParams } from 'react-router-dom';
import styled from 'styled-components';
import { RestaurantData, RestaurantListData } from '~/@types/api.types';
import { Region } from '~/@types/region.types';
import { getRestaurantsByAddress } from '~/api/restaurant';
import MiniRestaurantCard from '~/components/MiniRestaurantCard';
import { RECOMMENDED_REGION } from '~/constants/recommendedRegion';
import { SERVER_IMG_URL } from '~/constants/url';
import { FONT_SIZE } from '~/styles/common';

function RegionResultPage() {
  const { region } = useParams<{ region: Region }>();

  const { data: restaurantDataList } = useQuery<RestaurantListData>({
    queryKey: ['restaurantsFilteredByRegion', region],
    queryFn: () => getRestaurantsByAddress(RECOMMENDED_REGION[region].code),
  });

  return (
    <StyledContainer>
      <StyledBanner imgUrl={`${SERVER_IMG_URL}regions/${region}.jpeg`}>
        <StyledLink to="/">
          <StyledTitle> ← {RECOMMENDED_REGION[region].name.join(',')} 맛집</StyledTitle>
        </StyledLink>
      </StyledBanner>
      <StyledResultBox>
        <StyledResultCount>{restaurantDataList && restaurantDataList.content?.length}개의 매장</StyledResultCount>
        {restaurantDataList &&
          restaurantDataList.content?.map(({ celebs, ...restaurant }: RestaurantData) => (
            <MiniRestaurantCard
              key={`${restaurant.id}${celebs[0].id}`}
              restaurant={restaurant}
              celebs={celebs}
              showWaterMark={false}
            />
          ))}
      </StyledResultBox>
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

const StyledResultBox = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;

  width: 100%;

  padding: 0 1.6rem;
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

const StyledTitle = styled.h5`
  margin: 1.6rem 1.2rem;

  color: var(--white);
`;
