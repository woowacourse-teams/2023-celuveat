import { useQuery } from '@tanstack/react-query';
import { Link, useParams } from 'react-router-dom';
import styled from 'styled-components';
import { RestaurantListData } from '~/@types/api.types';
import { CelebId } from '~/@types/celeb.types';
import { getRestaurants } from '~/api/restaurant';
import ProfileImage from '~/components/@common/ProfileImage';
import SearchResultBox from '~/components/SearchResultBox';
import { WHOLE_BOUNDARY } from '~/constants/boundary';
import { CELEB } from '~/constants/celeb';
import { FONT_SIZE } from '~/styles/common';

function CelebResultPage() {
  const { celebId } = useParams();

  const { data: restaurantDataList } = useQuery<RestaurantListData>({
    queryKey: ['restaurants', celebId],
    queryFn: () =>
      getRestaurants({
        boundary: WHOLE_BOUNDARY,
        celebId: Number(celebId),
        sort: 'like',
      }),
  });

  return (
    <StyledContainer>
      <StyledLink to="/">
        <h5> ← {CELEB[Number(celebId) as CelebId].name} 추천 맛집</h5>
      </StyledLink>
      <StyledBanner>
        <ProfileImage
          imageUrl={CELEB[Number(celebId) as CelebId].profileImageUrl}
          name={CELEB[Number(celebId) as CelebId].name}
          size="72px"
        />
      </StyledBanner>
      <StyledResultCount>{restaurantDataList && restaurantDataList.totalElementsCount}개의 매장</StyledResultCount>
      <SearchResultBox restaurantDataList={restaurantDataList?.content} />
    </StyledContainer>
  );
}

export default CelebResultPage;

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

const StyledBanner = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 100%;
`;
