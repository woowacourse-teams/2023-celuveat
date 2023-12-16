import { css, styled } from 'styled-components';

import RestaurantReviewItem from '~/components/RestaurantReviewItem';
import { REVIEW_SHOW_COUNT } from '~/constants/options';
import useRestaurantReview from '~/hooks/server/useRestaurantReview';

interface RestaurantReviewListProps {
  isSummary?: boolean;
}

function RestaurantReviewList({ isSummary }: RestaurantReviewListProps) {
  const {
    restaurantReviewsData: { reviews },
  } = useRestaurantReview();

  return (
    <StyledRestaurantReviewListWrapper isSummary={isSummary}>
      <StyledRestaurantReviewList>
        {reviews.slice(0, isSummary ? REVIEW_SHOW_COUNT : reviews.length).map(review => (
          <RestaurantReviewItem key={review.id} review={review} />
        ))}
      </StyledRestaurantReviewList>
    </StyledRestaurantReviewListWrapper>
  );
}

export default RestaurantReviewList;

const StyledRestaurantReviewList = styled.div`
  flex-direction: column;
  gap: 4rem;
`;

const StyledRestaurantReviewListWrapper = styled.article<{ isSummary: boolean }>`
  margin-bottom: 2rem;

  ${({ isSummary }) =>
    !isSummary &&
    css`
      height: 500px;
      overflow-y: scroll;
    `}
`;
