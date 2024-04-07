import { useQuery } from '@tanstack/react-query';
import { useEffect } from 'react';
import styled from 'styled-components';
import { RestaurantData } from '~/@types/api.types';
import { getRestaurantWishList } from '~/api/user';
import LoginErrorHandleComponent from '~/components/@common/LoginErrorHandleComponent';
import MiniRestaurantCard from '~/components/MiniRestaurantCard';
import useBottomNavBarState from '~/hooks/store/useBottomNavBarState';
import { FONT_SIZE } from '~/styles/common';

function WishListPage() {
  const setWishListSelected = useBottomNavBarState(state => state.setWishListSelected);
  const { data: restaurantData } = useQuery<RestaurantData[]>({
    queryKey: ['restaurants', { type: 'wish-list' }],
    queryFn: () => getRestaurantWishList(),
  });

  useEffect(() => {
    setWishListSelected();
  }, []);

  return (
    <LoginErrorHandleComponent>
      <StyledContainer>
        <StyledTitle>위시리스트</StyledTitle>
        <StyledResultSection>
          {restaurantData?.length !== 0 ? (
            <>
              <StyledResultCount>{restaurantData?.length}개의 매장</StyledResultCount>
              {restaurantData?.map(({ celebs, ...restaurant }) => (
                <MiniRestaurantCard celebs={celebs} restaurant={restaurant} showRating showLike />
              ))}
            </>
          ) : (
            <>
              <h3>아직 좋아요를 누른 음식점이 없어요.</h3>
              <StyledDescription>
                위시리스트에 음식점을 추가하려면 음식점 카드의 좋아요 버튼을 눌러주세요!
              </StyledDescription>
            </>
          )}
        </StyledResultSection>
      </StyledContainer>
    </LoginErrorHandleComponent>
  );
}

export default WishListPage;

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

const StyledResultCount = styled.span`
  font-size: ${FONT_SIZE.md};
`;

const StyledResultSection = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;

  width: 100%;

  padding: 0 1.2rem;
`;

const StyledTitle = styled.h5`
  margin: 1.6rem 0.8rem 0;
`;

const StyledDescription = styled.div`
  font-size: ${FONT_SIZE.md};
`;
