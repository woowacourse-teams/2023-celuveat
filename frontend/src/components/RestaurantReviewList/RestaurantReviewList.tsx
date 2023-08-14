import { MutableRefObject, useEffect, useRef } from 'react';
import styled, { css } from 'styled-components';
import { shallow } from 'zustand/shallow';
import { RestaurantReview } from '~/@types/api.types';

import RestaurantReviewItem from '~/components/RestaurantReviewItem';
import useModalState from '~/hooks/store/useModalState';
import useMediaQuery from '~/hooks/useMediaQuery';

interface RestaurantReviewListProps {
  reviews: RestaurantReview[];
  modalContentRef?: MutableRefObject<HTMLDivElement>;
  isModal?: boolean;
}

function RestaurantReviewList({ reviews, modalContentRef, isModal = false }: RestaurantReviewListProps) {
  const targets = useRef<Record<number, HTMLDivElement>>({});
  const { isMobile } = useMediaQuery();
  const [isModalOpen, targetId, open, setId] = useModalState(
    state => [state.isModalOpen, state.targetId, state.open, state.setId],
    shallow,
  );

  useEffect(() => {
    const currentTarget = targets.current[targetId];

    if (isModalOpen && targetId === 0) {
      modalContentRef?.current.scrollTo({ top: 0, behavior: 'smooth' });
      return;
    }

    if (isModalOpen && currentTarget) {
      modalContentRef?.current.scrollTo({ top: currentTarget.offsetTop - 50, behavior: 'smooth' });
    }
  }, [isModalOpen]);

  const modalOpen: React.MouseEventHandler<HTMLDivElement> = e => {
    open();

    setId(Number(e.currentTarget.dataset.id));
  };

  return (
    <StyledRestaurantReviewListWrapper>
      <StyledRestaurantReviewList isMobile={isMobile || isModal}>
        {reviews?.map(review => (
          <RestaurantReviewItem
            reviewModalOpen={modalOpen}
            key={review.id}
            review={review}
            isModal={isModal}
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
