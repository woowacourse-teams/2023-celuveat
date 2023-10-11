import { useQuery } from '@tanstack/react-query';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { RestaurantData } from '~/@types/api.types';
import { getUpdatedRestaurants } from '~/api/restaurant';
import MiniRestaurantCard from '~/components/MiniRestaurantCard';
import { FONT_SIZE } from '~/styles/common';
import Banner from '~/assets/banner/banner.svg';

function UpdatedRestaurantPage() {
  const { data: restaurantData } = useQuery<RestaurantData[]>({
    queryKey: ['updatedRestaurants'],
    queryFn: getUpdatedRestaurants,
  });

  return (
    <StyledContainer>
      <StyledLink to="/">
        <StyledTitle>← 홈으로 돌아가기</StyledTitle>
      </StyledLink>
      <StyledBanner>
        <Banner />
      </StyledBanner>
      <StyledResultSection>
        <StyledResultCount>{restaurantData?.length}개의 매장</StyledResultCount>

        {restaurantData?.map(({ celebs, ...restaurant }) => (
          <MiniRestaurantCard celebs={celebs} restaurant={restaurant} showRating showLike />
        ))}
      </StyledResultSection>
      <div />
    </StyledContainer>
  );
}

export default UpdatedRestaurantPage;

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;

  width: 100%;
  min-height: 100vh;
  overflow-x: hidden;
`;

const StyledBanner = styled.div`
  width: 100%;
  height: 200px;
  max-height: 200px;

  object-fit: cover;

  overflow: hidden;
`;

const StyledResultCount = styled.span`
  font-size: ${FONT_SIZE.md};
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
  margin: 1.6rem 0.8rem 0;
`;
