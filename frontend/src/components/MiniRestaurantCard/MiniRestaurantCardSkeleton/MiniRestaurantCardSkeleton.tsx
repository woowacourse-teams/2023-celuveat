import { css, styled } from 'styled-components';
import Skeleton from '~/components/@common/Skeleton';
import { BORDER_RADIUS } from '~/styles/common';

interface MiniRestaurantCardProps {
  flexColumn?: boolean;
  isCarouselItem?: boolean;
}

function MiniRestaurantCardSkeleton({ flexColumn, isCarouselItem }: MiniRestaurantCardProps) {
  return (
    <StyledContainer flexColumn={flexColumn} isCarouselItem={isCarouselItem}>
      <Skeleton width="198px" height="188px" borderRadius={BORDER_RADIUS.xs} />
      <StyledDetail>
        <Skeleton width="198px" height="20px" borderRadius={BORDER_RADIUS.xs} />
        <Skeleton width="198px" height="12px" borderRadius={BORDER_RADIUS.xs} />
        <Skeleton width="198px" height="20px" borderRadius={BORDER_RADIUS.xs} />
      </StyledDetail>
    </StyledContainer>
  );
}

export default MiniRestaurantCardSkeleton;

const StyledContainer = styled.li<{ flexColumn: boolean; isCarouselItem: boolean }>`
  display: flex;

  ${({ isCarouselItem }) =>
    isCarouselItem &&
    css`
      margin: 0 auto;
    `}

  ${({ flexColumn }) =>
    flexColumn &&
    css`
      flex-direction: column;
      justify-content: start;
      align-items: center;

      width: 200px;
    `}

  cursor: pointer;
`;

const StyledDetail = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.6rem;

  padding: 0.8rem;
`;
