import { useQuery } from '@tanstack/react-query';
import { Link, useParams } from 'react-router-dom';
import styled from 'styled-components';
import { RestaurantData, RestaurantListData } from '~/@types/api.types';
import { getRestaurantsByAddress } from '~/api/restaurant';
import MiniRestaurantCard from '~/components/MiniRestaurantCard';
import { RECOMMENDED_REGION } from '~/constants/recommendedRegion';
import { SERVER_IMG_URL } from '~/constants/url';
import { FONT_SIZE } from '~/styles/common';

function RegionResultPage() {
  const { region } = useParams();

  const { data: restaurantDataList } = useQuery<RestaurantListData>({
    queryKey: ['restaurantsFilteredByRegion', region],
    queryFn: () => getRestaurantsByAddress(region),
    keepPreviousData: true,
  });

  return (
    <StyledContainer>
      <Link to="/" style={{ textDecoration: 'none' }}>
        <h5> ← {RECOMMENDED_REGION[region as keyof typeof RECOMMENDED_REGION]}</h5>
      </Link>
      <StyledBanner src={`${SERVER_IMG_URL}regions/${region}.jpeg`} alt={region} />
      <StyledResultCount>{restaurantDataList && restaurantDataList.content?.length}개의 매장</StyledResultCount>
      <StyledResultBox>
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

  width: 100vw;

  padding: 5.6rem 1.2rem;
`;

const StyledResultCount = styled.span`
  font-size: ${FONT_SIZE.md};
`;

const StyledResultBox = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;
`;

const StyledBanner = styled.img`
  width: 100%;
  max-height: 160px;

  overflow: hidden;
  object-fit: cover;

  border-radius: 12px;
`;
