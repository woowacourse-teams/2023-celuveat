import { useRef } from 'react';
import styled, { css } from 'styled-components';
import { RestaurantReview } from '~/@types/api.types';

import RestaurantReviewItem from '~/components/RestaurantReviewItem';
import useMediaQuery from '~/hooks/useMediaQuery';

interface RestaurantReviewListProps {
  reviews: RestaurantReview[];
  // modalContentRef?: MutableRefObject<HTMLDivElement>;
  isModal?: boolean;
}

function RestaurantReviewList({ reviews, isModal = false }: RestaurantReviewListProps) {
  const targets = useRef<Record<number, HTMLDivElement>>({});
  const { isMobile } = useMediaQuery();

  // useEffect(() => {
  //   const currentTarget = targets.current[reviewId];

  //   if (isModalOpen && reviewId === 0) {
  //     modalContentRef?.current.scrollTo({ top: 0, behavior: 'smooth' });
  //     return;
  //   }

  //   if (isModalOpen && currentTarget) {
  //     modalContentRef?.current.scrollTo({ top: currentTarget.offsetTop - 50, behavior: 'smooth' });
  //   }
  // }, [isModalOpen]);

  return (
    <StyledRestaurantReviewListWrapper>
      <StyledRestaurantReviewList isMobile={isMobile || isModal}>
        {reviews?.map(review => (
          <RestaurantReviewItem
            key={review.id}
            review={review}
            isInModal={isModal}
            ref={ref => {
              targets.current[review.id] = ref;
            }}
          />
        ))}
      </StyledRestaurantReviewList>
    </StyledRestaurantReviewListWrapper>
  );
}

export default RestaurantReviewList;

const StyledRestaurantReviewList = styled.div<{ isMobile: boolean }>`
  display: grid;
  grid-template-columns: 1fr 1fr;

  gap: 4rem;

  ${({ isMobile }) =>
    isMobile &&
    css`
      grid-template-columns: 1fr;
    `};
`;

const StyledRestaurantReviewListWrapper = styled.article`
  margin-bottom: 2rem;
`;
