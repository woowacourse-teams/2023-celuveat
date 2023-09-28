import { useQuery } from '@tanstack/react-query';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';
import { RestaurantData, RestaurantListData } from '~/@types/api.types';
import { getRestaurantsByAddress } from '~/api/restaurant';
import MiniRestaurantCard from '~/components/MiniRestaurantCard';
import { FONT_SIZE } from '~/styles/common';

function ResultPage() {
  const { region } = useParams();

  const { data: restaurantDataList } = useQuery<RestaurantListData>({
    queryKey: ['restaurantsFilteredByRegion', region],
    queryFn: () => getRestaurantsByAddress(region),
    keepPreviousData: true,
  });

  return (
    <StyledContainer>
      <h5> ← 압구정 청담</h5>
      <StyledResultCount>12개의 매장</StyledResultCount>
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

export default ResultPage;

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;

  width: 100vw;
  overflow-x: hidden;

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
