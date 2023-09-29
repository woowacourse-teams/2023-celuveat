import { useQuery } from '@tanstack/react-query';
import { Link, useParams } from 'react-router-dom';
import styled from 'styled-components';
import { RestaurantData, RestaurantListData } from '~/@types/api.types';
import { getRestaurants } from '~/api/restaurant';
import ProfileImage from '~/components/@common/ProfileImage';
import MiniRestaurantCard from '~/components/MiniRestaurantCard';
import { CELEB } from '~/constants/celeb';
import { FONT_SIZE } from '~/styles/common';

function CelebResultPage() {
  const { celebId } = useParams();

  const { data: restaurantDataList } = useQuery<RestaurantListData>({
    queryKey: ['restaurants', celebId],
    queryFn: () =>
      getRestaurants({
        boundary: { lowLatitude: '32', highLatitude: '40', lowLongitude: '120', highLongitude: '132' },
        celebId: Number(celebId),
        category: '전체',
        page: 0,
        sort: 'like',
      }),
    keepPreviousData: true,
  });

  return (
    <StyledContainer>
      <Link to="/" style={{ textDecoration: 'none' }}>
        <h5> ← {CELEB[Number(celebId) as keyof typeof CELEB].name} 추천 맛집</h5>
      </Link>
      <StyledBanner>
        <ProfileImage
          imageUrl={CELEB[Number(celebId) as keyof typeof CELEB].profileImageUrl}
          name={CELEB[Number(celebId) as keyof typeof CELEB].name}
          size="72px"
        />
      </StyledBanner>
      <StyledResultCount>{restaurantDataList && restaurantDataList.totalElementsCount}개의 매장</StyledResultCount>
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

export default CelebResultPage;

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

const StyledBanner = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 100%;
`;
