import { useQuery } from '@tanstack/react-query';
import styled, { css } from 'styled-components';
import { RestaurantData } from '~/@types/api.types';
import { getRestaurantDetail } from '~/api/restaurant';
import RestaurantDetailLikeButton from '~/components/RestaurantDetailLikeButton';
import Naver from '~/assets/icons/oauth/naver.svg';
import SuggestionButton from '~/components/SuggestionButton';
import { BORDER_RADIUS, FONT_SIZE } from '~/styles/common';

interface LinkContainerProps {
  isMobile: boolean;
  restaurantId: string;
  celebId: string;
}

function LinkContainer({ isMobile, restaurantId, celebId }: LinkContainerProps) {
  const {
    data: { celebs, ...restaurant },
  } = useQuery<RestaurantData>({
    queryKey: ['restaurantDetail', restaurantId, celebId],
    queryFn: async () => getRestaurantDetail(restaurantId, celebId),
    suspense: true,
  });

  const openNewWindow =
    (url: string): React.MouseEventHandler<HTMLButtonElement> =>
    () =>
      window.open(url, '_blank');

  return (
    <StyledLinkContainer isMobile={isMobile} tabIndex={0}>
      <StyledMainLinkContainer isMobile={isMobile}>
        <RestaurantDetailLikeButton restaurantId={restaurantId} celebId={celebId} />
        <button type="button" onClick={openNewWindow(restaurant.naverMapUrl)}>
          <Naver width={32} />
          <div>네이버 지도로 보기</div>
        </button>
      </StyledMainLinkContainer>
      <SuggestionButton />
    </StyledLinkContainer>
  );
}

export default LinkContainer;

const StyledLinkContainer = styled.aside<{ isMobile: boolean }>`
  display: flex;
  flex-direction: column;

  height: 100%;

  ${({ isMobile }) =>
    isMobile
      ? css`
          display: none;
        `
      : css`
          position: sticky;
          top: 80px;

          width: 33%;
        `}
`;

const StyledMainLinkContainer = styled.div<{ isMobile: boolean }>`
  display: flex;
  flex-direction: column;
  gap: 1.2rem;

  width: 100%;

  padding: 2.4rem;

  ${({ isMobile }) =>
    isMobile
      ? css`
          border-radius: ${BORDER_RADIUS.md} ${BORDER_RADIUS.md} 0 0;
          background: var(--white);
        `
      : css`
          margin-top: 4.8rem;

          border: 1px solid var(--gray-2);
          border-radius: ${BORDER_RADIUS.md};
        `}

  box-shadow: var(--shadow);

  & > button {
    display: flex;
    align-items: center;
    gap: 0 4rem;

    padding: 1.6rem 3.2rem;

    border: none;
    border-radius: ${BORDER_RADIUS.md};

    font-family: SUIT-Medium, sans-serif;
    font-size: ${FONT_SIZE.md};

    & > div {
      color: var(--white);
    }

    &:first-child {
      background: var(--red-2);
    }

    &:nth-child(2) {
      background: #03c75a;
    }
  }
`;
