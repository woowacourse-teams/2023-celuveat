import { useQuery } from '@tanstack/react-query';
import { useParams } from 'react-router-dom';
import { useMemo } from 'react';
import { shallow } from 'zustand/shallow';
import styled from 'styled-components';
import { getMSWRestaurantReview } from '~/api';

import RestaurantReviewList from '~/components/RestaurantReviewList';
import ReviewForm from '~/components/ReviewForm/ReviewForm';
import DeleteButton from '~/components/ReviewForm/DeleteButton';
import { Modal, ModalContent } from '~/components/@common/Modal';

import useModalState from '~/hooks/store/useModalState';
import { FONT_SIZE } from '~/styles/common';

import type { RestaurantReview } from '~/@types/api.types';

const REVIEW_SHOW_COUNT = 6;

const isMoreThan = (target: number, standard: number) => target > standard;

function RestaurantReviewWrapper() {
  const { id } = useParams();
  const { data: restaurantReviews, isLoading } = useQuery<RestaurantReview[]>({
    queryKey: ['restaurantReview'],
    queryFn: () => getMSWRestaurantReview(id),
  });
  const [content, isModalOpen, close, open, setId, setContent] = useModalState(
    state => [state.content, state.isModalOpen, state.close, state.open, state.setId, state.setContent],
    shallow,
  );

  const reviewCount = restaurantReviews?.length;
  const previewReviews = useMemo(() => restaurantReviews?.slice(0, REVIEW_SHOW_COUNT), [restaurantReviews]);
  const isMoreReviews = isMoreThan(reviewCount, REVIEW_SHOW_COUNT);

  const openAllReviews = () => {
    setContent('후기 모두 보기');
    setId(0);
    open();
  };

  const openFormModal = () => {
    setContent('리뷰 작성 하기');
    open();
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
      <button type="button" onClick={openFormModal}>
        리뷰 작성하기
      </button>
      <Modal>
        {content === '후기 모두 보기' && (
          <ModalContent isShow={isModalOpen} title={content} closeModal={close}>
            <RestaurantReviewList reviews={restaurantReviews} isModal />
          </ModalContent>
        )}
        {content === '리뷰 작성 하기' && (
          <ModalContent isShow={isModalOpen} title={content} closeModal={close}>
            <ReviewForm type="create" />
          </ModalContent>
        )}
        {content === '리뷰 수정 하기' && (
          <ModalContent isShow={isModalOpen} title={content} closeModal={close}>
            <ReviewForm type="update" />
          </ModalContent>
        )}
        {content === '리뷰 삭제 하기' && (
          <ModalContent isShow={isModalOpen} title={content} closeModal={close}>
            <DeleteButton />
          </ModalContent>
        )}
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
