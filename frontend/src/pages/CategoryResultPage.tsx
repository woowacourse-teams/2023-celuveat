import { useQuery } from '@tanstack/react-query';
import { Link, useParams } from 'react-router-dom';
import styled from 'styled-components';
import { RestaurantData, RestaurantListData } from '~/@types/api.types';
import { getRestaurants } from '~/api/restaurant';
import MiniRestaurantCard from '~/components/MiniRestaurantCard';
import { WHOLE_BOUNDARY } from '~/constants/boundary';
import { FONT_SIZE } from '~/styles/common';

function CategoryResultPage() {
  const { category } = useParams();

  const { data: restaurantDataList } = useQuery<RestaurantListData>({
    queryKey: ['restaurants', category],
    queryFn: () =>
      getRestaurants({
        boundary: WHOLE_BOUNDARY,
        category,
        sort: 'like',
      }),
  });

  return (
    <StyledContainer>
      <StyledLink to="/">
        <h5> ← {category} </h5>
      </StyledLink>
      <StyledResultCount>{restaurantDataList && restaurantDataList.totalElementsCount}개의 매장</StyledResultCount>
      <StyledResultBox>
        {restaurantDataList &&
          restaurantDataList.content?.map(({ celebs, ...restaurant }: RestaurantData) => (
            <MiniRestaurantCard
              key={`${restaurant.id}${celebs[0].id}`}
              restaurant={restaurant}
              celebs={celebs}
              carousel
            />
          ))}
      </StyledResultBox>
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

const StyledResultBox = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;
`;
