import { useMemo } from 'react';
import { shallow } from 'zustand/shallow';
import styled from 'styled-components';

import RestaurantReviewList from '~/components/RestaurantReviewList';
import ReviewForm from '~/components/ReviewForm/ReviewForm';
import DeleteButton from '~/components/ReviewForm/DeleteButton';
import { Modal, ModalContent } from '~/components/@common/Modal';

import useModalState from '~/hooks/store/useModalState';
import { FONT_SIZE } from '~/styles/common';

import useRestaurantReview from '~/hooks/server/useRestaurantReview';

const REVIEW_SHOW_COUNT = 6;

const isMoreThan = (target: number, standard: number) => target > standard;

function RestaurantReviewWrapper() {
  const { restaurantReviewsData, isLoading } = useRestaurantReview();
  const [content, isModalOpen, close, open, setId, setContent] = useModalState(
    state => [state.content, state.isModalOpen, state.close, state.open, state.setId, state.setContent],
    shallow,
  );

  const reviewCount = restaurantReviewsData?.totalElementsCount;
  const previewReviews = useMemo(
    () => restaurantReviewsData?.reviews.slice(0, REVIEW_SHOW_COUNT),
    [restaurantReviewsData],
  );
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
      <StyledReviewCountText>후기 {reviewCount ? `${reviewCount}개` : '없음'}</StyledReviewCountText>
      {isLoading && <div>로딩중입니다.</div>}
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
            <RestaurantReviewList reviews={restaurantReviewsData?.reviews} isModal />
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
