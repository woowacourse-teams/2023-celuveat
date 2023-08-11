import { styled, css } from 'styled-components';

import { useQuery } from '@tanstack/react-query';
import { FONT_SIZE } from '~/styles/common';

import RestaurantWishItem from '~/components/RestaurantWishItem/RestaurantWishItem';
import useMediaQuery from '~/hooks/useMediaQuery';
import { userMSWInstance } from '~/api/User';

import type { RestaurantData } from '~/@types/api.types';

function RestaurantWishList() {
  const { isMobile } = useMediaQuery();

  const { data: restaurantData } = useQuery<RestaurantData[]>({
    queryKey: ['restaurants', 'like'],
    queryFn: () => userMSWInstance.get('restaurants/like').then(response => response.data),
  });

  return (
    <StyledRestaurantCardListContainer>
      {restaurantData?.length !== 0 ? (
        <StyledRestaurantCardList isMobile={isMobile}>
          {restaurantData?.map(({ celebs, ...restaurant }) => (
            <RestaurantWishItem restaurant={restaurant} celebs={celebs} />
          ))}
        </StyledRestaurantCardList>
      ) : (
        <>
          <h3>일치하는 음식점이 없어요.</h3>
          <StyledDescription>
            더 많은 음식점을 둘러보려면 필터링 옵션 살펴보거나 지도를 움직여 주세요!
          </StyledDescription>
        </>
      )}
    </StyledRestaurantCardListContainer>
  );
}

export default RestaurantWishList;

const StyledRestaurantCardListContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 3.2rem;

  margin: 3.2rem 2.4rem;
`;

const StyledDescription = styled.div`
  font-size: ${FONT_SIZE.md};
`;

const StyledRestaurantCardList = styled.div<{ isMobile: boolean }>`
  display: grid;
  gap: 4rem 2.4rem;

  height: 100%;

  grid-template-columns: 1fr 1fr 1fr 1fr 1fr;

  @media screen and (width <= 1240px) {
    grid-template-columns: 1fr 1fr 1fr;
  }

  ${({ isMobile }) =>
    isMobile
      ? css`
          grid-template-columns: 1fr 1fr;

          @media screen and (width <= 550px) {
            grid-template-columns: 1fr;
          }
        `
      : css`
          @media screen and (width <= 743px) {
            grid-template-columns: 1fr;
          }
        `}
`;
