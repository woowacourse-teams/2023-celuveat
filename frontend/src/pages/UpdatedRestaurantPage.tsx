import { useQuery } from '@tanstack/react-query';
import { Link } from 'react-router-dom';
import styled, { css } from 'styled-components';
import { RestaurantData } from '~/@types/api.types';
import { getUpdatedRestaurants } from '~/api/restaurant';
import MiniRestaurantCard from '~/components/MiniRestaurantCard';
import { SERVER_IMG_URL } from '~/constants/url';
import useMediaQuery from '~/hooks/useMediaQuery';
import { FONT_SIZE } from '~/styles/common';

function UpdatedRestaurantPage() {
  const { isMobile } = useMediaQuery();
  const { data: restaurantData } = useQuery<RestaurantData[]>({
    queryKey: ['updatedRestaurants'],
    queryFn: getUpdatedRestaurants,
    suspense: true,
  });

  return (
    <StyledContainer>
      <StyledLink to="/">
        <StyledTitle>← 홈으로 돌아가기</StyledTitle>
      </StyledLink>
      <StyledBanner alt="최근 업데이트된 맛집" src={`${SERVER_IMG_URL}banner/recent-updated.jpg`} isMobile={isMobile} />
      <StyledResultSection>
        <StyledResultCount>{restaurantData?.length}개의 매장</StyledResultCount>

        {restaurantData?.map(({ celebs, ...restaurant }) => (
          <MiniRestaurantCard celebs={celebs} restaurant={restaurant} showRating showLike />
        ))}
      </StyledResultSection>
    </StyledContainer>
  );
}

export default UpdatedRestaurantPage;

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;

  width: 100%;
  max-width: 1240px;
  min-height: 100vh;

  margin: 0 auto;
  overflow-x: hidden;

  padding-bottom: 4.8rem;
`;

const StyledBanner = styled.img<{ isMobile: boolean }>`
  width: 100%;

  margin: 0 auto;

  object-fit: cover;

  overflow: hidden;

  ${({ isMobile }) =>
    isMobile
      ? css`
          height: 200px;
        `
      : css`
          height: 240px;

          margin: 1.2rem;
        `}
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
