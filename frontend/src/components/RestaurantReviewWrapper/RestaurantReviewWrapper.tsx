import { useQuery } from '@tanstack/react-query';
import { useParams } from 'react-router-dom';
import { useMemo } from 'react';
import { shallow } from 'zustand/shallow';
import styled from 'styled-components';
import { getRestaurantReview } from '~/api';

import RestaurantReviewList from '~/components/RestaurantReviewList';

import type { RestaurantReview } from '~/@types/api.types';
import { Modal, ModalContent } from '~/components/@common/Modal';
import useModalState from '~/hooks/store/useModalState';
import { FONT_SIZE } from '~/styles/common';

const REVIEW_SHOW_COUNT = 6;

const isMoreThan = (target: number, standard: number) => target > standard;

function RestaurantReviewWrapper() {
  const { id } = useParams();
  const { data: restaurantReviews, isLoading } = useQuery<RestaurantReview[]>({
    queryKey: ['restaurantReview'],
    queryFn: () => getRestaurantReview(id),
  });
  const [isModalOpen, close, open, setId] = useModalState(
    state => [state.isModalOpen, state.close, state.open, state.setId],
    shallow,
  );

  const reviewCount = restaurantReviews?.length;
  const previewReviews = useMemo(() => restaurantReviews?.slice(0, REVIEW_SHOW_COUNT), [restaurantReviews]);
  const isMoreReviews = isMoreThan(reviewCount, REVIEW_SHOW_COUNT);

  const openAllReviews = () => {
    open();
    setId(0);
  };

  return (
    <StyledRestaurantReviewWrapper>
      {isLoading && <div>로딩중입니다.</div>}
      <StyledReviewCountText>{`후기 ${reviewCount}개`}</StyledReviewCountText>
      <RestaurantReviewList reviews={previewReviews} />
      {isMoreReviews && (
        <StyledAllReviewsButton
          type="button"
          onClick={openAllReviews}
        >{`후기 ${reviewCount}개 모두 보기`}</StyledAllReviewsButton>
      )}
      <Modal>
        <ModalContent isShow={isModalOpen} title="후기 모두 보기" closeModal={close}>
          <RestaurantReviewList reviews={restaurantReviews} isModal />
        </ModalContent>
      </Modal>
    </StyledRestaurantReviewWrapper>
  );
}

export default RestaurantReviewWrapper;

const StyledRestaurantReviewWrapper = styled.article`
  margin-bottom: 4rem;
`;

const StyledReviewCountText = styled.h5`
  margin-bottom: 2rem;
`;

const StyledAllReviewsButton = styled.button`
  padding: 1.3rem 2.3rem;

  border: 1px solid #222;
  border-radius: 8px;
  background: transparent;

  font-size: ${FONT_SIZE.md};
  outline: none;

  &:hover {
    background: var(--gray-1);
  }
`;
