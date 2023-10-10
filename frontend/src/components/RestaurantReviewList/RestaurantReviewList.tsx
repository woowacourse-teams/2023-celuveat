import { styled } from 'styled-components';
import { RestaurantReview } from '~/@types/api.types';

import RestaurantReviewItem from '~/components/RestaurantReviewItem';
import useMediaQuery from '~/hooks/useMediaQuery';

interface RestaurantReviewListProps {
  reviews: RestaurantReview[];
  isModal?: boolean;
}

function RestaurantReviewList({ reviews, isModal = false }: RestaurantReviewListProps) {
  const { isMobile } = useMediaQuery();

  return (
    <StyledRestaurantReviewListWrapper>
      <StyledRestaurantReviewList isMobile={isMobile || isModal}>
        {reviews?.map(review => (
          <RestaurantReviewItem key={review.id} review={review} isInModal={isModal} />
        ))}
      </StyledRestaurantReviewList>
    </StyledRestaurantReviewListWrapper>
  );
}

export default RestaurantReviewList;

const StyledRestaurantReviewList = styled.div<{ isMobile: boolean }>`
  display: flex;
  flex-direction: column;
  gap: 4rem;
`;

const StyledRestaurantReviewListWrapper = styled.article`
  margin-bottom: 2rem;
`;
